package com.example.meetupapp.di

import com.example.meetupapp.database.FirestoreModule
import com.example.meetupapp.di.module.AddEventModule
import com.example.meetupapp.di.module.EventDetailViewModule
import com.example.meetupapp.di.module.ProfileModule
import com.example.meetupapp.di.module.StartModule
import com.example.meetupapp.di.module.ViewModelFactoryModule
import com.example.meetupapp.ui.addEvent.AddEventFragment
import com.example.meetupapp.ui.eventDetailView.EventDetailViewFragment
import com.example.meetupapp.ui.profile.ProfileFragment
import com.example.meetupapp.ui.start.StartFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        StartModule::class,
        ViewModelFactoryModule::class,
        ProfileModule::class,
        FirestoreModule::class,
        AddEventModule::class,
        EventDetailViewModule::class,
    ]
)
interface AppComponent {

    fun inject(startFragment: StartFragment)
    fun inject(profileFragment: ProfileFragment)
    fun inject(addEventFragment: AddEventFragment)
    fun inject(eventDetailViewFragment: EventDetailViewFragment)


}