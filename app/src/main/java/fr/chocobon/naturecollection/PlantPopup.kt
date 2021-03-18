package fr.chocobon.naturecollection

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import fr.chocobon.naturecollection.adapter.PlantAdapter

class PlantPopup(private val adapter: PlantAdapter, private val currentPlant: PlantModel) : Dialog(adapter.context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.popup_plants_details)
        setupComponents()
        setupCloseButton()
        setupDeleteButton()
        setupStarButton()
    }

    private fun updateStar(button: ImageView){
        if(currentPlant.liked){
            button.setImageResource(R.drawable.ic_star)
        }
        else{
            button.setImageResource(R.drawable.ic_unstar)
        }
    }

    private fun setupStarButton() {
        //Recuperer
        val starButton = findViewById<ImageView>(R.id.star_button)
        updateStar(starButton)

        //Interaction
        starButton.setOnClickListener{
            currentPlant.liked =! currentPlant.liked
            val repo = PlantRepository()
            repo.updatePlant(currentPlant)
            updateStar(starButton)
        }
    }

    private fun setupDeleteButton() {
        findViewById<ImageView>(R.id.delete_button).setOnClickListener{
            //Supprimer la plante de base
            val repo = PlantRepository()
            repo.deletePlant(currentPlant)
            dismiss()
        }
    }

    private fun setupCloseButton() {
        findViewById<ImageView>(R.id.close_button).setOnClickListener{
            //Fermer la fenetre
            dismiss()
        }
    }

    private fun setupComponents() {
        //Actualiser l'image de la plante
        val plantImage = findViewById<ImageView>(R.id.image_item)
        Glide.with(adapter.context).load(Uri.parse(currentPlant.imageUrl)).into(plantImage)

        //Actualiser le nom de la plante
        findViewById<TextView>(R.id.popup_plant_name).text = currentPlant.name

        //Actualiser la description de la plante
        findViewById<TextView>(R.id.popup_plant_description_subtitle).text = currentPlant.description

        //Actualiser la croissance de la plante
        findViewById<TextView>(R.id.popup_plant_grow_subtitle).text = currentPlant.grow

        //Actualiser la consommation d'eau de la plante
        findViewById<TextView>(R.id.popup_plant_water_subtitle).text = currentPlant.water
    }
}