package fr.chocobon.naturecollection.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.chocobon.naturecollection.*

class PlantAdapter(
        val context: MainActivity,
        private val plantList : List<PlantModel>,
        private val layoutId: Int
) : RecyclerView.Adapter<PlantAdapter.ViewHolder>() {

    //Boite pour ranger tout les composants à controler
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        //image de la plante
        val plantImage = view.findViewById<ImageView>(R.id.image_item)
        val plantName: TextView? = view.findViewById(R.id.name_item)
        val planDescription: TextView? = view.findViewById(R.id.description_item)
        val starIcon = view.findViewById<ImageView>(R.id.star_icon)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(layoutId, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = plantList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //recuperer les informations de la plante
        val currentPlant = plantList[position]

        //Recuperer le repository
        val repo = PlantRepository()

        //utiliser glide our recuperer l'image à partir de son lien
        Glide.with(context).load(Uri.parse(currentPlant.imageUrl)).into(holder.plantImage)

        //Mettre a jour le nom de la plante
        holder.plantName?.text = currentPlant.name

        //mettre a jour la description de la plante
        holder.planDescription?.text = currentPlant.description

        //Verifier si la plante a été liké

        if(currentPlant.liked){
            holder.starIcon.setImageResource(R.drawable.ic_star)
        }
        else{
            holder.starIcon.setImageResource(R.drawable.ic_unstar)
        }

        //Rajouter une interaction sur l'etoile
        holder.starIcon.setOnClickListener{

            //Inverser si le bouton est like ou non
            currentPlant.liked = !currentPlant.liked

            //Mettre à jour l'objet plante
            repo.updatePlant(currentPlant)
        }

        //Interaction lors du clic sur une plante
        holder.itemView.setOnClickListener{
            //Afficher la popup
            PlantPopup(this, currentPlant).show()
        }
    }
}