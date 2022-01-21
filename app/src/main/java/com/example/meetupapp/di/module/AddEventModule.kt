package com.example.meetupapp.di.module

import androidx.lifecycle.ViewModel
import com.example.meetupapp.di.ViewModelKey
import com.example.meetupapp.ui.addEvent.AddEventViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface AddEventModule {

    @Binds
    @IntoMap
    @ViewModelKey(AddEventViewModel::class)
    fun bindAddEventViewModel(addEventViewModel: AddEventViewModel): ViewModel

}