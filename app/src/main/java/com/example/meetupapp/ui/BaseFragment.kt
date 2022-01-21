package com.example.meetupapp.ui

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

abstract class BaseFragment(@LayoutRes layoutRes: Int) : Fragment(layoutRes) {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

}