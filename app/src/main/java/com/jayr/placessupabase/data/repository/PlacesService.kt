package com.jayr.placessupabase.data.repository

import com.jayr.placessupabase.data.models.Place

interface PlacesService {
    suspend fun createPlace(place:Place):Place    // Create data
    suspend fun getAllPlaces(): List<Place>       // Read data
    suspend fun updatePlace(place:Place):Place    // Update data
    suspend fun insertImage(fileName: String, fileBytes: ByteArray): String? // upload media
    suspend fun deletePlace(id:Int)               // Delete data
}