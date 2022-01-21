package com.example.meetupapp.database.model

data class UserDatabaseEntry(
    var id: String = "",
    var name: String = "",
    var friendList: List<String> = listOf(),
    var eventList: List<String> = listOf(),
    var imageID: String = ""
)

