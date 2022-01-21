package com.example.meetupapp.database.model

data class CommentDatabaseEntry(
    val commentId: String = "",
    val ownerId: String = "",
    val eventId: String = "",
    val comment: String = "",
    val commentDate: String = "",
    )