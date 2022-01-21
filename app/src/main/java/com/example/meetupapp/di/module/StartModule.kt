package com.example.meetupapp.di.module

import androidx.lifecycle.ViewModel
import com.example.meetupapp.di.ViewModelKey
import com.example.meetupapp.ui.start.StartViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface StartModule {

    @Binds
    @IntoMap
    @ViewModelKey(StartViewModel::class)
    fun bindStartViewModel(startViewModel: StartViewModel): ViewModel

}