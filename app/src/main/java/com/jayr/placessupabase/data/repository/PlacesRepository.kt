package com.jayr.placessupabase.data.repository

import com.jayr.placessupabase.data.models.Place
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from

class PlacesRepository: PlacesService {

    val supabase = createSupabaseClient(
        supabaseUrl = "https://sqxvwtprjedpgekjyylf.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InNxeHZ3dHByamVkcGdla2p5eWxmIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTQzOTMxNjEsImV4cCI6MjA2OTk2OTE2MX0.6ymw204VHHxalSgWGtxJVPxsD-1IFvzzE_PgDJ5PMvg"
    ) {
        install(Auth)
        install(Postgrest)
        //install other modules
    }

    override suspend fun createPlace(place: Place): Place {
      return  supabase.from("places").insert(place) {
            select()
        }.decodeSingle<Place>()
    }

    override suspend fun getAllPlaces(): List<Place> {
        return supabase.from("places").select().decodeList()
    }

    override suspend fun updatePlace(place: Place): Place {
        TODO("Not yet implemented")
    }

    override suspend fun deletePlace(id: Int) {
        TODO("Not yet implemented")
    }

}