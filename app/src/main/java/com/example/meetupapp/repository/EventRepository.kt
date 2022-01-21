package com.example.meetupapp.repository

import android.util.Log
import com.example.meetupapp.database.EventDatabaseOperations
import com.example.meetupapp.database.model.EventDatabaseEntry
import com.example.meetupapp.database.model.UserDatabaseEntry
import com.example.meetupapp.models.EventCategory
import com.example.meetupapp.models.MeetingEventItem
import com.example.meetupapp.models.MyMeetingEventItem
import com.example.meetupapp.models.User
import java.lang.Exception
import javax.inject.Inject

private const val TAG: String = "START_REPO"

class EventRepository @Inject constructor(
    private val eventDatabaseOperations: EventDatabaseOperations,
    private val userRepository: UserRepository,
) {

    suspend fun getAllEvents(): List<MeetingEventItem> {
        return eventDatabaseOperations.getAllEventEntries()
            .mapNotNull { userRepository.getUserById(it.organizerID)?.let { organizer -> it.toEvent(organizer) } }
            .sortedWith(compareBy({ it.startDate }, { it.startTime }))
    }

    suspend fun getMyEvents(): List<MyMeetingEventItem> {
        val user = userRepository.getLoggedInUser()?:return emptyList()
        return user.eventList
            .mapNotNull { eventDatabaseOperations.getEventById(it)?.toMyEvent() }
            .sortedWith(compareBy({ it.startDate }, { it.startTime }))
    }

    suspend fun addUserInputToEventDatabase(
        title: String,
        location: String = "",
        startDate: String,
        endDate: String,
        startTime: String,
        endTime: String,
        category: String,
        maxParticipants: String = "0",
        active: Boolean = true,
        canceled: Boolean = false,
        canceledMessage: String = "",
        timeStamp: String
    ) {
        val eventDatabaseEntry = EventDatabaseEntry(
            title = title,
            location = location,
            startDate = startDate,
            endDate = endDate,
            startTime = startTime,
            endTime = endTime,
            category = category,
            organizerID = userRepository.getLoggedInUser()?.id ?: run { "" /* TODO: Throw Exception, event need a organizer */},
            participants = userRepository.getLoggedInUser()?.let{ listOf(it.id) }?: run { listOf() /* TODO: Throw Exception */ },
            maxParticipants = maxParticipants.toLong(),
            active = active,
            canceled = canceled,
            canceledMessage = canceledMessage,
            timeStamp = timeStamp
        )

        eventDatabaseOperations.addToDatabase(eventDatabaseEntry)
    }

    /*
    * get all event entries from firestore and sorts them by their timestamp
    * get the latest event from user
    * */
     suspend fun getLastAddedEventFromUser(user: User): EventDatabaseEntry?{
        for(event in eventDatabaseOperations.getAllEventEntries().sortedByDescending { it.timeStamp }){
            if(event.organizerID == user.id){
                return event
            }
        }
        return null
    }

    // << Converter >>

    private fun EventDatabaseEntry.toMyEvent(): MyMeetingEventItem {
        return MyMeetingEventItem(
            id = id,
            title = title,
            location = location,
            startDate = startDate,
            endDate = endDate,
            startTime = startTime,
            endTime = endTime,
            category = getCategory(category),
        )
    }

    private suspend fun convertArrayToList(userArray: List<String>): List<User> {
        val userList: MutableList<User> = mutableListOf()
        for (id in userArray) {
            userRepository.getUserById(id)?.let{
                userList.add(userRepository.convertDbUserToUser(it))
            }
        }
        return userList
    }

    private suspend fun EventDatabaseEntry.toEvent(organizer: UserDatabaseEntry): MeetingEventItem {
        return MeetingEventItem(
            id = id,
            title = title,
            location = location,
            startDate = startDate,
            endDate = endDate,
            startTime = startTime,
            endTime = endTime,
            organizer = userRepository.convertDbUserToUser(organizer),
            participants = convertArrayToList(participants),
            maxParticipants = maxParticipants,
            category = getCategory(category)
        )
    }

    //Event specific converter
    private fun getCategory(category: String): EventCategory {
        return when (category) {
            "Sport" -> EventCategory.SPORT
            "Social" -> EventCategory.SOCIAL
            "Online" -> EventCategory.ONLINE
            "General" -> EventCategory.GENERAL
            else -> EventCategory.GENERAL
        }
    }

}





