package com.example.meetupapp.di.module

import androidx.lifecycle.ViewModel
import com.example.meetupapp.di.ViewModelKey
import com.example.meetupapp.ui.eventDetailView.EventDetailViewViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface EventDetailViewModule {

    @Binds
    @IntoMap
    @ViewModelKey(EventDetailViewViewModel::class)
    fun bindEventDetailViewViewModel(eventDetailViewViewModel: EventDetailViewViewModel): ViewModel
}