package com.anushmp.autocompleteplaces

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    lateinit var searchForPlaces: MaterialButton

    //places sdk API key -
    val apikey = "AIzaSyABGWh4fOrCuKLdFIi8pxGg1NP_roQkkYa0"
    lateinit var placesClient: PlacesClient

    //private val autocomplete_request_code = 1
    val fields = listOf(Place.Field.ID,
                        Place.Field.NAME,
                        Place.Field.ADDRESS)

    var launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){

        if(it.resultCode == Activity.RESULT_OK){

            val placeIntent = it.data

            val place = Autocomplete.getPlaceFromIntent(placeIntent!!)

            searchForPlaces.append(place.name)
            searchForPlaces.append("\n")
            searchForPlaces.append(place.address)
        }

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchForPlaces = findViewById(R.id.searchForPlaces)

        Places.initialize(applicationContext, apikey)
        placesClient = Places.createClient(this)


        searchForPlaces.setOnClickListener {

            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN,
            fields).build(this)


            launcher.launch(intent)

        }

    }
}