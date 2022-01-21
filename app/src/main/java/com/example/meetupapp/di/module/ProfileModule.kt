package com.example.meetupapp.di.module

import androidx.lifecycle.ViewModel
import com.example.meetupapp.di.ViewModelKey
import com.example.meetupapp.ui.profile.ProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ProfileModule {

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    fun bindProfileViewModel(profileViewModel: ProfileViewModel): ViewModel

}