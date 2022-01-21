package com.example.meetupapp.models

class MyMeetingEventItem(
        var id: String,
        var title: String,
        var location: String,
        var startDate: String,
        var endDate: String,
        var startTime: String,
        var endTime: String,
        var category: EventCategory
)