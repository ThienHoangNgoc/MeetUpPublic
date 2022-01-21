package com.example.meetupapp.database

import android.util.Log
import com.example.meetupapp.database.model.EventDatabaseEntry
import com.example.meetupapp.database.model.UserDatabaseEntry
import com.example.meetupapp.models.MyMeetingEventItem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import com.example.meetupapp.models.User

private const val TAG: String = "START_REPO"

class UserDatabaseOperations @Inject constructor(private val db: FirebaseFirestore) {

    fun addToDatabase(userEntry: UserDatabaseEntry) {
        val user = hashMapOf(
            userNameKey to userEntry.name,
            userFriendListKey to userEntry.friendList,
            userEventListKey to userEntry.eventList,
            userImageIDKey to userEntry.imageID
        )
        db.collection(userDatabase)
            .add(user)
            .addOnSuccessListener { documentReference -> Log.d(TAG, "$documentReference success") }
            .addOnFailureListener { exception -> Log.w(TAG, "Exception: $exception") }
    }

    suspend fun getAllEntries(): List<UserDatabaseEntry> =
        suspendCancellableCoroutine<List<UserDatabaseEntry>> { continuation ->
            val databaseUserList = mutableListOf<UserDatabaseEntry>()
            db.collection(userDatabase).get().addOnSuccessListener { result ->
                for (document in result) {
                    databaseUserList.add(document.getUser())
                }
                continuation.resumeWith(Result.success(databaseUserList))
            }.addOnFailureListener {
                continuation.resumeWith(Result.failure(it))
            }
        }

    suspend fun getUserById(id: String): UserDatabaseEntry? =
        suspendCancellableCoroutine<UserDatabaseEntry?> { continuation ->
            db.collection(userDatabase).get().addOnSuccessListener { result ->
                for (document in result) {
                    if (document.id == id) {
                        continuation.resumeWith(Result.success(document.getUser()))
                        return@addOnSuccessListener
                    }
                }
                continuation.resumeWith(Result.success(null))
            }.addOnFailureListener {
                continuation.resumeWith(Result.failure(it))
            }
        }

    suspend fun updateUser(user: User, propertyKey: String, propertyValue: String) {
        db.collection(userDatabase).document(user.id).update(propertyKey, propertyValue)
    }

    /*
        get eventList from user and add the newest event to the eventList. Then update the user in the db

     */
    suspend fun updateMyEvents(user: User, latestEvent: EventDatabaseEntry){
        val list: MutableList<String> = user.eventList.toMutableList()
        list.add(latestEvent.id)
        db.collection(userDatabase).document(user.id).update(userEventListKey, list)
    }

    //TODO update event -> participation List and update (same as user) || User -> MyEventList get list add event then add new list to user

    private fun QueryDocumentSnapshot.getUser(): UserDatabaseEntry {
        return UserDatabaseEntry(
            id = id,
            name = data.getValue(userNameKey).toString(),
            friendList = data.getValue(userFriendListKey) as List<String>,
            eventList = data.getValue(userEventListKey) as List<String>,
            imageID = data.getValue(userImageIDKey).toString()
        )
    }

}