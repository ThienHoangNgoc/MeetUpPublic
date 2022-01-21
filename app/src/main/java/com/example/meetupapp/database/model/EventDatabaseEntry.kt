package com.example.meetupapp.database.model

data class EventDatabaseEntry(
    val id: String = "",
    val title: String = "",
    val location: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val startTime: String = "",
    val endTime: String = "",
    val organizerID: String = "",
    val participants: List<String> = listOf(),
    val maxParticipants: Long = 0,
    val category: String = "",
    val active: Boolean = false,
    val canceled: Boolean = false,
    val canceledMessage: String = "",
    val timeStamp: String = "",
)

