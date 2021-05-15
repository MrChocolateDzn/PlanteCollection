package fr.chocobon.naturecollection

import android.net.Uri
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import fr.chocobon.naturecollection.PlantRepository.Singleton.databaseRef
import fr.chocobon.naturecollection.PlantRepository.Singleton.downloadUri
import fr.chocobon.naturecollection.PlantRepository.Singleton.plantList
import fr.chocobon.naturecollection.PlantRepository.Singleton.storageReference
import java.util.*
import javax.security.auth.callback.Callback

class PlantRepository {

    object Singleton {
        // Donner le lien pour acceder au bucket
        private val BUCKET_URL: String = "gs://naturecollection-80401.appspot.com"

        // Se connecter à notre espace de stockage
        val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(BUCKET_URL)

        // Se connecter à la reference "plants"
        val databaseRef = FirebaseDatabase.getInstance().getReference("plants")

        // créer une liste qui va contenir nos plantes
        val plantList = arrayListOf<PlantModel>()

        //Contenir le lien de l'image courante
        var downloadUri: Uri?= null
    }

    fun updateData(callback: ()-> Unit){
        //absorber les données depuis la databaseRef -> liste de plantes
        databaseRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                //Retier les anciennes
                plantList.clear()
                //Recolter la liste
                for(ds in snapshot.children){
                    //Construire un objet plante
                    val plant = ds.getValue(PlantModel::class.java)

                    // verifier que la plante n'est pas null
                    if(plant != null) {
                        //ajouter la plante à notre liste
                        plantList.add(plant)
                    }
                }
                //Actionner le callback
                callback()
            }

        })
    }
    //Creer une fonction pour envoyer des fichiers sur le storage
    fun uploadImage(file: Uri?, callback: () -> Unit){
        //Verifier que ce fichier n'est pas null
        if(file !=null){
            val fileName = UUID.randomUUID().toString() + ".jpg"
            val ref = storageReference.child(fileName)
            val uploadTask = ref.putFile(file)

            //Demarrer la tache d'envoi
            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>>{ task ->
                // Si il y'a eu un probleme lors de l'envoi du fichier
                if(!task.isSuccessful){
                    task.exception?.let{throw it}
                }

                return@Continuation ref.downloadUrl
            }).addOnCompleteListener{ task ->
                //Vérifier si tout a bien fonctionné
                if(task.isSuccessful){
                    //Recuperer l'image
                    downloadUri = task.result
                    callback()
                }
            }
        }
    }
    //Mettre à jour un objet plante en Base de données
    fun updatePlant(plant: PlantModel){
        databaseRef.child(plant.id).setValue(plant)
    }

    //Inserer une nouvelle plante en Base de données
    fun insertPlant(plant: PlantModel){
        databaseRef.child(plant.id).setValue(plant)
    }

    //Supprimer une plate de la base
    fun deletePlant(plant: PlantModel) = databaseRef.child(plant.id).removeValue()
}