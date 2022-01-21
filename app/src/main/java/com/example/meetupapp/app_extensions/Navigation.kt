package com.example.meetupapp.app_extensions

import androidx.fragment.app.Fragment
import com.example.meetupapp.ui.main.MainActivity

fun Fragment.navigateToFragment(targetFragment: Fragment) {
    (this.activity as MainActivity).getFragNavController().pushFragment(targetFragment)
}