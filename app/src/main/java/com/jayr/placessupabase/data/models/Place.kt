package com.jayr.placessupabase.data.models

import kotlinx.serialization.Serializable


@Serializable
data class Place(
    val name:String,
    val description:String,
    val image_url:String,
    val id:Int?= null,
    val created_at:String? = null
)
