package com.example.meetupapp.ui.start.adapter.viewholder

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meetupapp.databinding.MyMeetingEventListBinding
import com.example.meetupapp.models.StartViewItem
import com.example.meetupapp.ui.start.adapter.MyEventsItemAdapter

class MyEventListViewHolder(val binding: MyMeetingEventListBinding) : RecyclerView.ViewHolder(binding.root) {

    fun initLayout(holder: MyEventListViewHolder, position: Int, startViewItemList: List<StartViewItem>) {
        val itemList = (startViewItemList[position] as StartViewItem.MyEventsList).myMeetingEventItemList
        val adapter = MyEventsItemAdapter()
        adapter.updateListData(itemList)
        with(holder.binding) {
            val recyclerView = holder.binding.myMeetingEventsRecyclerView
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(root.context, RecyclerView.HORIZONTAL, false)
        }

    }
}
