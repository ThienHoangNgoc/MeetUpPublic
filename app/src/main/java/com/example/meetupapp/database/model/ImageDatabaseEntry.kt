package com.example.meetupapp.database.model

data class ImageDatabaseEntry (
    val imageID: String = "",
    //Todo: change to Drawable when db is available
    val imageDrawable: Int = 0
)