package com.example.meetupapp.ui.eventDetailView

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.meetupapp.AppApplication
import com.example.meetupapp.R
import com.example.meetupapp.databinding.FragmentEventDetailViewBinding
import com.example.meetupapp.ui.BaseFragment
import com.example.meetupapp.ui.utils.viewBinding

class EventDetailViewFragment: BaseFragment(R.layout.fragment_event_detail_view) {

    private val binding: FragmentEventDetailViewBinding by viewBinding(FragmentEventDetailViewBinding::bind)

    private val eventDetailViewViewModel: EventDetailViewViewModel by viewModels(factoryProducer = {viewModelFactory})

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as AppApplication).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }



}