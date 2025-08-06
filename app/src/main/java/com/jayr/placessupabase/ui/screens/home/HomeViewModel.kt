package com.jayr.placessupabase.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jayr.placessupabase.data.models.Place
import com.jayr.placessupabase.data.repository.PlacesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
// states
     private val _places = MutableStateFlow<List<Place>>(listOf())
     val places: StateFlow<List<Place>> get() = _places

    private val placesRepository = PlacesRepository() // instance of the places repository

// init
    init {
        getPlaces()
    }

// methods
    fun getPlaces()
    {
       viewModelScope.launch {  _places.value = placesRepository.getAllPlaces() }
    }

    fun createPlace(place:Place){
        viewModelScope.launch {
            placesRepository.createPlace(place)
            getPlaces()
        }
    }

    fun insertImage(fileName:String, fileBytes: ByteArray ){
        viewModelScope.launch {
            placesRepository.insertImage(fileName, fileBytes)
        }
    }
}