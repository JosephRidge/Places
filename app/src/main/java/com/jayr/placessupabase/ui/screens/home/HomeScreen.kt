package com.jayr.placessupabase.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jayr.placessupabase.data.models.Place

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = viewModel(),
    modifier: Modifier
){
    val places = homeViewModel.places.collectAsState()

    var name by remember{ mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var image_url by remember { mutableStateOf("") }



    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = places.value.toString(),
            modifier = modifier
        )
        TextField(
            value = name,
            onValueChange = {
                name =it
            }
        )
        TextField(
            value = description,
            maxLines = 2,
            onValueChange = {
                description =it
            }
        )
        TextField(
            value = image_url,
            onValueChange = {
                image_url = it
            }
        )

        Button(onClick = {
            var place = Place(
                name, description, image_url
            )
            homeViewModel.createPlace(place)
        }) {
            Text("add place")
        }
    }

}