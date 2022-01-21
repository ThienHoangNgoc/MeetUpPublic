package com.example.meetupapp.repository

import com.example.meetupapp.database.UserDatabaseOperations
import com.example.meetupapp.database.model.EventDatabaseEntry
import com.example.meetupapp.database.model.UserDatabaseEntry
import com.example.meetupapp.models.User
import java.lang.Exception
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDatabaseOperations: UserDatabaseOperations,
    private val imageRepository: ImageRepository,
) {

    suspend fun getLoggedInUser(): User? =
        userDatabaseOperations.getAllEntries().firstOrNull()?.let {
            convertDbUserToUser(it) }

    fun convertDbUserToUser(dbUser: UserDatabaseEntry): User {
        return User(
            id = dbUser.id,
            name = dbUser.name,
            friendList = dbUser.friendList.toList(),
            eventList = dbUser.eventList.toList(),
            profileImage = imageRepository.getImageByID(dbUser.imageID).imageDrawable
        )
    }

    suspend fun getUserById(userID: String): UserDatabaseEntry? = userDatabaseOperations.getUserById(userID)

    suspend fun updateEventListOfUser(latestEvent: EventDatabaseEntry){
        val user = getLoggedInUser()?:throw Exception("User can't be null")
        userDatabaseOperations.updateMyEvents(user, latestEvent)
    }



}