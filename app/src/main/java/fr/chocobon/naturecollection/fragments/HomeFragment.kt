package fr.chocobon.naturecollection.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import fr.chocobon.naturecollection.MainActivity
import fr.chocobon.naturecollection.PlantModel
import fr.chocobon.naturecollection.PlantRepository.Singleton.plantList
import fr.chocobon.naturecollection.R
import fr.chocobon.naturecollection.adapter.PlantAdapter
import fr.chocobon.naturecollection.adapter.PlantItemDecoration

class HomeFragment(
        private val context : MainActivity
)
    : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_home, container, false)

        /*
        //Creer une liste qui va stocker ces plantes
        val plantList = arrayListOf<PlantModel>()

        //Enregistrer une premiere plante dans notre liste (pissenlit)
        plantList.add(PlantModel(
                "Pissenlit",
                "jaune soleil",
                "https://cdn.pixabay.com/photo/2014/05/01/17/51/dandelion-335662_1280.jpg",
                false
        ))

        //Enregistrer une deuxième plante dans notre liste (rose)
        plantList.add(PlantModel(
                "Rose",
                "ça pique un peu",
                "https://cdn.pixabay.com/photo/2018/05/16/22/27/rose-3407234_1280.jpg",
                false
        ))

        //Enregistrer une troisième plante dans notre liste (cactus)
        plantList.add(PlantModel(
                "Cactus",
                "ça pique beaucoup",
                "https://cdn.pixabay.com/photo/2016/07/06/20/47/prickly-pear-1501307_1280.jpg",
                false
        ))

        //Enregistrer une quatrième plante dans notre liste (tulipe)
        plantList.add(PlantModel(
                "Tulipe",
                "c'est beau",
                "https://cdn.pixabay.com/photo/2017/03/13/21/19/tulip-2141216_1280.jpg",
                false
        ))
        */

        //Recuperer le RecycleView
        val horizontalRecyclerView = view.findViewById<RecyclerView>(R.id.horizontal_recycler_view)
        horizontalRecyclerView.adapter = PlantAdapter(context, plantList.filter { !it.liked }, R.layout.item_horizontal_plant)

        //Recuperer le second RecycleView
        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.vertical_recycler_view)
        verticalRecyclerView.adapter = PlantAdapter(context, plantList, R.layout.item_vertical_plant)
        verticalRecyclerView.addItemDecoration(PlantItemDecoration())
        return view
    }
}