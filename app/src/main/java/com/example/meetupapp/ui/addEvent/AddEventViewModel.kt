package com.example.meetupapp.ui.addEvent

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.meetupapp.app_extensions.UtilDateTime
import com.example.meetupapp.models.EventCategory
import com.example.meetupapp.models.getCategoryType
import com.example.meetupapp.repository.EventRepository
import com.example.meetupapp.repository.UserRepository
import io.grpc.okhttp.internal.Util
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class AddEventViewModel @Inject constructor(
        private val eventRepository: EventRepository,
        private val userRepository: UserRepository,
) : ViewModel() {

    suspend fun addEventToDatabase(
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
            timestamp: String = UtilDateTime.getLocalTimeStamp()
    ) {
        eventRepository.addUserInputToEventDatabase(
            title,
            location,
            startDate,
            endDate,
            startTime,
            endTime,
            category,
            maxParticipants,
            active,
            canceled,
            canceledMessage,
            timestamp
        )
    }

    suspend fun updateUserEvents() {
        val user = userRepository.getLoggedInUser() ?: throw Exception("User can't be null")
        eventRepository.getLastAddedEventFromUser(user)?.let { userRepository.updateEventListOfUser(it) }
    }

    fun convertStringToDate(dateAsString: String): Date {
        val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        return simpleDateFormat.parse(dateAsString) ?: throw Exception("Given String is not a date.")

    }

    fun getTimeFromDateTime(dateTimeText: String): String {
        val simpleDateTimeFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        val dateTime = simpleDateTimeFormat.parse(dateTimeText) ?: throw Exception("String is not a Date.")
        val simpleTimeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        return simpleTimeFormat.format(dateTime).toString()
    }

    fun getDateFromDateTime(dateTimeText: String): String {
        val simpleDateTimeFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        val dateTime = simpleDateTimeFormat.parse(dateTimeText) ?: throw Exception("String is not a Date.")
        val simpleTimeFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return simpleTimeFormat.format(dateTime).toString()
    }

    fun getEndTime(dateTimeText: String): String {
        return if (dateTimeText.isNotEmpty()) {
            getTimeFromDateTime(dateTimeText)
        } else ""
    }

    fun getEndDate(dateTimeText: String): String {
        return if (dateTimeText.isNotEmpty()) {
            getDateFromDateTime(dateTimeText)
        } else ""
    }

    fun getCheckedCategory(context: Context): String {
        when {
            EventCategory.GENERAL.checked -> {
                return EventCategory.GENERAL.getCategoryType(context)
            }
            EventCategory.SOCIAL.checked -> {
                return EventCategory.SOCIAL.getCategoryType(context)
            }
            EventCategory.SPORT.checked -> {
                return EventCategory.SPORT.getCategoryType(context)
            }
            EventCategory.ONLINE.checked -> {
                return EventCategory.ONLINE.getCategoryType(context)
            }
            else -> {
                return EventCategory.GENERAL.getCategoryType(context)
            }
        }
    }

}


