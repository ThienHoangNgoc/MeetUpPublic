package com.example.meetupapp.di.module

import androidx.lifecycle.ViewModelProvider
import com.example.meetupapp.di.DefaultViewModelFactory
import dagger.Binds
import dagger.Module

@Module
interface ViewModelFactoryModule {

    @Binds
    fun bindViewModelFactory(factory: DefaultViewModelFactory): ViewModelProvider.Factory

}