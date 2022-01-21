package com.example.meetupapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.meetupapp.R
import com.example.meetupapp.databinding.ActivityMainBinding
import com.example.meetupapp.ui.start.StartFragment
import com.example.meetupapp.ui.profile.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ncapdevi.fragnav.FragNavController


private const val INDEX_Start = 0
private const val INDEX_PROFILE = 1

class MainActivity : AppCompatActivity(), FragNavController.RootFragmentListener {

    private lateinit var fragNavController: FragNavController

    override val numberOfRootFragments: Int = 2
    override fun getRootFragment(index: Int): Fragment {
        when (index) {
            INDEX_Start -> return StartFragment()
            INDEX_PROFILE -> return ProfileFragment()
        }
        throw IllegalStateException("Need to send an index that we know")
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        fragNavController = FragNavController(supportFragmentManager, binding.navHostFragment.id)
        fragNavController.rootFragmentListener = this

        fragNavController.initialize(FragNavController.TAB1, savedInstanceState)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    fragNavController.switchTab(INDEX_Start)
                    true
                }
                R.id.navigation_profile -> {
                    fragNavController.switchTab(INDEX_PROFILE)
                    true
                }
                else -> {
                    false
                }
            }
        })

    }

    fun getFragNavController(): FragNavController {
        return fragNavController
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        fragNavController.onSaveInstanceState(outState)
    }

    override fun onBackPressed() {
        try {
            if (!fragNavController.popFragment()) {
                super.onBackPressed()
            }
        } catch (e: UnsupportedOperationException) {
            super.onBackPressed()
        }
    }


}