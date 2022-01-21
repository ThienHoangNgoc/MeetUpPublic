package com.example.meetupapp.ui.start.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.meetupapp.databinding.MyMeetingEventItemBinding
import com.example.meetupapp.models.MyMeetingEventItem
import com.example.meetupapp.models.getColor

class MyEventsItemAdapter : RecyclerView.Adapter<MyEventsItemAdapter.ViewHolder>() {

    private var myMeetingEventList: List<MyMeetingEventItem> = listOf()

    class ViewHolder(val binding: MyMeetingEventItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MyMeetingEventItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.binding.root.context
        with(holder.binding) {
            with(myMeetingEventList[position]) {
                myMeetingEventTitle.text = title
                myMeetingEventDate.text = startDate
                myMeetingEventLocation.text = location
                myMeetingEventStartTime.text = startTime
                myMeetingEventLayout.strokeColor = category.getColor(context)
                myMeetingEventLayout.setOnClickListener(View.OnClickListener {
                    //Todo: go to Event
                })
            }
        }
    }

    override fun getItemCount(): Int {
        return myMeetingEventList.size
    }

    fun updateListData(list: List<MyMeetingEventItem>) {
        myMeetingEventList = list
    }

}

