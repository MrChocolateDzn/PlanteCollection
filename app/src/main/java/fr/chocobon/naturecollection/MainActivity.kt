package fr.chocobon.naturecollection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.chocobon.naturecollection.fragments.AddPlantFragment
import fr.chocobon.naturecollection.fragments.CollectionFragment
import fr.chocobon.naturecollection.fragments.HomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFragment(HomeFragment(this))

        //importer la bottomnavigationview
        val navigationView = findViewById<BottomNavigationView>(R.id.navigation_view)
        navigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home_page -> {
                    loadFragment(HomeFragment(this))
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.collection_page -> {
                    loadFragment(CollectionFragment(this))
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.add_plan_page -> {
                    loadFragment(AddPlantFragment(this))
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }
        loadFragment(HomeFragment(this))
    }
    private fun loadFragment(fragment: Fragment){
        //Charger notre plantRepository
        val repo = PlantRepository()

        //Mettre à jour la liste de plantes
        repo.updateData {
            // Injecter le fragment dans notre boite
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}