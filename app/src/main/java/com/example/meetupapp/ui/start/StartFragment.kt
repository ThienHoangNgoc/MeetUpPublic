package com.example.meetupapp.ui.start

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meetupapp.AppApplication
import com.example.meetupapp.R
import com.example.meetupapp.app_extensions.navigateToFragment
import com.example.meetupapp.databinding.FragmentStartBinding
import com.example.meetupapp.ui.BaseFragment
import com.example.meetupapp.ui.addEvent.AddEventFragment
import com.example.meetupapp.ui.myEvents.MyEventsFragment
import com.example.meetupapp.ui.allEvents.AllEventsFragment
import com.example.meetupapp.ui.start.adapter.StartItemAdapter
import com.example.meetupapp.ui.utils.viewBinding
import kotlinx.coroutines.launch

class StartFragment : BaseFragment(R.layout.fragment_start), StartFragmentClickHandler {

    private val binding by viewBinding(FragmentStartBinding::bind)

    private val startViewModel: StartViewModel by viewModels(factoryProducer = { viewModelFactory })

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as AppApplication).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val meetUpAdapter = StartItemAdapter(this)

        startViewModel.meetingEventItems
            .observe(viewLifecycleOwner, onChanged = { startViewItems ->
                meetUpAdapter.updateListData(startViewItems)
            })

        binding.meetingRecyclerView.adapter = meetUpAdapter
        binding.meetingRecyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.addEventButton.setOnClickListener { this.navigateToFragment(AddEventFragment()) }

        binding.currentDateText.text = startViewModel.getCurrentDate()
        viewLifecycleOwner.lifecycleScope.launch {
            binding.welcomeMessageText.text = startViewModel.getWelcomeMessage()
        }

    }

    override fun onShowAllOpenEventsClicked() {
        this.navigateToFragment(AllEventsFragment())
    }

    override fun onShowAllMyEventsClicked() {
        this.navigateToFragment(MyEventsFragment())
    }

    override fun onEventItemClicked() {
        TODO("Not yet implemented")
    }

}

