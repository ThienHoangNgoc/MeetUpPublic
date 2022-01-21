package com.example.meetupapp.models

class User(
    var id:String,
    var name:String,
    var friendList : List<String>,
    var eventList: List<String>,
    var profileImage: Int
)