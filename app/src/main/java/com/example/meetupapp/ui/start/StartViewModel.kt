package com.example.meetupapp.ui.start

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.meetupapp.R
import com.example.meetupapp.app_extensions.convertDate
import com.example.meetupapp.models.MeetingEventItem
import com.example.meetupapp.models.MyMeetingEventItem
import com.example.meetupapp.models.StartViewItem
import com.example.meetupapp.repository.EventRepository
import com.example.meetupapp.repository.UserRepository
import com.example.meetupapp.utils.DateUtils
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject
import com.example.meetupapp.app_extensions.convertStringToDateComparable
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class StartViewModel @Inject constructor(
        private val eventRepository: EventRepository,
        private val userRepository: UserRepository,
) : ViewModel() {

    val meetingEventItems: LiveData<List<StartViewItem>> by lazy {
        liveData {
            coroutineScope {

                val myEvents = async { eventRepository.getMyEvents() }
                val allEvents = async { eventRepository.getAllEvents() }
                emit(createStartViewItems(myEvents.await(), allEvents.await()))
            }
        }
    }

    fun getCurrentDate(): String {
        return DateUtils.getCurrentDate()
    }

    suspend fun getWelcomeMessage(): String {
        val userName = userRepository.getLoggedInUser()?.name
        return "Hallo, $userName"
    }

    //Todo: Remove past events from DB

    @OptIn(ExperimentalStdlibApi::class)
    private fun createStartViewItems(
            myEvents: List<MyMeetingEventItem>,
            allEvents: List<MeetingEventItem>,
    ): List<StartViewItem> {
        return buildList {
            add(StartViewItem.SectionHeaderItem(R.string.my_events_title))
            if (myEvents.isEmpty() || getMyFutureEvents(myEvents).isEmpty()) {
                add(StartViewItem.EmptyMyEventsList)
                add(StartViewItem.ShowAllItemsTextBtn(showTextBtn = false, isMyEvents = true))
            } else {
                add(StartViewItem.ShowAllItemsTextBtn(showTextBtn = true, isMyEvents = true))
                add(StartViewItem.MyEventsList(getMyFutureEvents(myEvents)))
            }
            add(StartViewItem.SectionHeaderItem(R.string.all_events_title))
            if (allEvents.isEmpty() || getAllFutureEvents(allEvents).isEmpty()) {
                add(StartViewItem.EmptyStartViewMeetingEventItem)
                add(StartViewItem.ShowAllItemsTextBtn(showTextBtn = false, isMyEvents = false))
            }else {
                add(StartViewItem.ShowAllItemsTextBtn(true, isMyEvents = false))
                var oldDate = ""
                var newDate: String
                getAllFutureEvents(allEvents).forEach { event ->
                    newDate = event.startDate
                    if (oldDate != newDate) {
                        add(StartViewItem.DateHeaderItem(newDate.convertDate()))
                        add(StartViewItem.StartViewMeetingEventItem(event))
                        oldDate = newDate
                    } else {
                        add(StartViewItem.StartViewMeetingEventItem(event))
                    }
                }
                add(StartViewItem.ShowAllItemsBtn)
            }

        }
    }

    //TOdo: Date() doesn't work as intended -> inspect mit LocalDateTime.isNow() ersetzen
    @OptIn(ExperimentalStdlibApi::class)
    private fun getAllFutureEvents(allEvents: List<MeetingEventItem>): List<MeetingEventItem> {
        return buildList {
            allEvents.forEach { event ->
                if(Date() < (event.startDate.convertStringToDateComparable())){
                    add(event)
                }
            }
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun getMyFutureEvents(allEvents: List<MyMeetingEventItem>): List<MyMeetingEventItem> {
        return buildList {
            allEvents.forEach { event ->
                if(Date() < (event.startDate.convertStringToDateComparable())){
                    add(event)
                }
            }
        }
    }

}
