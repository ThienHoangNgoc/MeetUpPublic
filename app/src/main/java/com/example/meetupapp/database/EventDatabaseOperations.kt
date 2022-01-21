package com.example.meetupapp.database

import com.example.meetupapp.database.model.EventDatabaseEntry
import com.example.meetupapp.models.MeetingEventItem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject

class EventDatabaseOperations @Inject constructor(private val db: FirebaseFirestore) {

    fun addToDatabase(eventEntry: EventDatabaseEntry) {
        val event: MutableMap<String, Any> = HashMap()
        event[eventTitleKey] = eventEntry.title
        event[eventLocationKey] = eventEntry.location
        event[eventStartDateKey] = eventEntry.startDate
        event[eventEndDateKey] = eventEntry.endDate
        event[eventStartTimeKey] = eventEntry.startTime
        event[eventEndTimeKey] = eventEntry.endTime
        event[eventOrganizerIDKey] = eventEntry.organizerID
        event[eventParticipantsKey] = eventEntry.participants
        event[eventMaxParticipantsKey] = eventEntry.maxParticipants
        event[eventCategoryKey] = eventEntry.category
        event[eventActiveKey] = eventEntry.active
        event[eventCanceledKey] = eventEntry.canceled
        event[eventCanceledMessageKey] = eventEntry.canceledMessage
        event[eventTimeStampKey] = eventEntry.timeStamp
        db.collection(eventDatabase).add(event)
    }

    suspend fun getAllEventEntries(): List<EventDatabaseEntry> =
        suspendCancellableCoroutine<List<EventDatabaseEntry>> { continuation ->
            val databaseEventList = mutableListOf<EventDatabaseEntry>()
            db.collection(eventDatabase).get().addOnSuccessListener { result ->
                for (document in result) {
                    databaseEventList.add(getEventFromDocument(document))
                }
                continuation.resumeWith(Result.success(databaseEventList))
            }.addOnFailureListener {
                continuation.resumeWith(Result.failure(it))
            }
        }

    suspend fun getEventById(id: String): EventDatabaseEntry? =
        suspendCancellableCoroutine<EventDatabaseEntry?> { continuation ->
            db.collection(eventDatabase).get().addOnSuccessListener { result ->
                for (document in result) {
                    if (document.id == id) {
                        continuation.resumeWith(Result.success(getEventFromDocument(document)))
                        return@addOnSuccessListener
                    }
                }
                continuation.resumeWith(Result.success(null))
            }.addOnFailureListener {
                continuation.resumeWith(Result.failure(it))
            }
        }

    suspend fun updateEvent(event: MeetingEventItem, propertyKey: String, propertyValue: String) {
        db.collection(userDatabase).document(event.id).update(propertyKey, propertyValue)
    }

    private fun getEventFromDocument(document: QueryDocumentSnapshot): EventDatabaseEntry {
        return EventDatabaseEntry(
            document.id,
            document.data.getValue(eventTitleKey).toString(),
            document.data.getValue(eventLocationKey).toString(),
            document.data.getValue(eventStartDateKey).toString(),
            document.data.getValue(eventEndDateKey).toString(),
            document.data.getValue(eventStartTimeKey).toString(),
            document.data.getValue(eventEndTimeKey).toString(),
            document.data.getValue(eventOrganizerIDKey).toString(),
            document.data.getValue(eventParticipantsKey) as List<String>,
            document.data.getValue(eventMaxParticipantsKey) as Long,
            document.data.getValue(eventCategoryKey).toString(),
            document.data.getValue(eventActiveKey) as Boolean,
            document.data.getValue(eventCanceledKey) as Boolean,
            document.data.getValue(eventCanceledMessageKey).toString(),
            document.data.getValue(eventTimeStampKey).toString(),
        )
    }

}