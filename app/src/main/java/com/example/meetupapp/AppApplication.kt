package com.example.meetupapp

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.meetupapp.database.UserDatabaseOperations
import com.example.meetupapp.di.AppComponent
import com.example.meetupapp.di.DaggerAppComponent
import com.google.firebase.firestore.FirebaseFirestore

class AppApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }

}