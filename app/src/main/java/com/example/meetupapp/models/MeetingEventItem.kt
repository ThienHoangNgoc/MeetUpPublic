package com.example.meetupapp.models

class MeetingEventItem(
        var id: String,
        var title: String,
        var location: String,
        var startDate: String,
        var endDate: String,
        var startTime: String,
        var endTime: String,
        var organizer: User,
        var participants: List<User>,
        var maxParticipants: Long,
        var category: EventCategory,
)