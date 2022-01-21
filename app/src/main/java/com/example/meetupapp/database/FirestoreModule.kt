package com.example.meetupapp.database

import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides

@Module
class FirestoreModule {

    @Provides
    fun provideFirestore() = FirebaseFirestore.getInstance()

}