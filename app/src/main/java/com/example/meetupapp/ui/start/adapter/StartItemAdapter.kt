package com.example.meetupapp.ui.start.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.meetupapp.databinding.DateHeaderItemBinding
import com.example.meetupapp.databinding.EmptyMeetingEventItemBinding
import com.example.meetupapp.databinding.EmptyMyMeetingEventsListBinding
import com.example.meetupapp.databinding.MeetingEventItemBinding
import com.example.meetupapp.databinding.MyMeetingEventListBinding
import com.example.meetupapp.databinding.SectionHeaderItemBinding
import com.example.meetupapp.databinding.ShowAllItemsBtnBinding
import com.example.meetupapp.databinding.ShowAllItensTextBtnBinding
import com.example.meetupapp.models.StartViewItem
import com.example.meetupapp.ui.start.adapter.viewholder.DateHeaderViewHolder
import com.example.meetupapp.ui.start.adapter.viewholder.EmptyMeetingEventViewHolder
import com.example.meetupapp.ui.start.adapter.viewholder.EmptyMyEventListViewHolder
import com.example.meetupapp.ui.start.adapter.viewholder.MeetingEventViewHolder
import com.example.meetupapp.ui.start.adapter.viewholder.MyEventListViewHolder
import com.example.meetupapp.ui.start.adapter.viewholder.SectionHeaderViewHolder
import com.example.meetupapp.ui.start.adapter.viewholder.ShowAllItemsBtnViewHolder
import com.example.meetupapp.ui.start.adapter.viewholder.ShowAllItemsTextBtnViewHolder

class StartItemAdapter(private val startFragment: Fragment) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var startViewItemList: List<StartViewItem> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            SECTION_HEADER_TYPE -> {
                val binding = SectionHeaderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return SectionHeaderViewHolder(binding)
            }
            DATE_HEADER_TYPE -> {
                val binding = DateHeaderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return DateHeaderViewHolder(binding)
            }
            MEETING_EVENT_TYPE -> {
                val binding = MeetingEventItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return MeetingEventViewHolder(binding)
            }
            SHOW_ALL_ITEMS_BTN_TYPE -> {
                val binding = ShowAllItemsBtnBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ShowAllItemsBtnViewHolder(binding)
            }
            MY_EVENTS_LIST_TYPE -> {
                val binding = MyMeetingEventListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return MyEventListViewHolder(binding)
            }
            EMPTY_MEETING_EVENT_TYPE -> {
                val binding = EmptyMeetingEventItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return EmptyMeetingEventViewHolder(binding)
            }
            EMPTY_MY_EVENT_LIST_TYPE -> {
                val binding =
                    EmptyMyMeetingEventsListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return EmptyMyEventListViewHolder(binding)
            }
            SHOW_ALL_ITEMS_TEXT_BTN_TYPE -> {
                val binding = ShowAllItensTextBtnBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ShowAllItemsTextBtnViewHolder(binding)
            }

            else -> {
                throw RuntimeException("The type has to be one of the following : SECTION_HEADER_TYPE, DATE_HEADER_TYPE, MEETING_EVENT_TYPE, SHOW_ALL_ITEMS_BTN_TYPE, MY_EVENT_LIST_TYPE.")
            }

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SectionHeaderViewHolder -> holder.bind(startViewItemList[position] as StartViewItem.SectionHeaderItem)
            is DateHeaderViewHolder -> holder.initLayout(holder, position, startViewItemList)
            is MeetingEventViewHolder -> holder.initLayout(holder, position, startViewItemList)
            is ShowAllItemsBtnViewHolder -> holder.initLayout(holder, position, startFragment)
            is MyEventListViewHolder -> holder.initLayout(holder, position, startViewItemList)
            is ShowAllItemsTextBtnViewHolder -> holder.initLayout(holder, position, startViewItemList, startFragment)
        }
    }

    override fun getItemCount(): Int {
        return startViewItemList.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (startViewItemList[position]) {
            is StartViewItem.SectionHeaderItem -> SECTION_HEADER_TYPE
            is StartViewItem.DateHeaderItem -> DATE_HEADER_TYPE
            is StartViewItem.StartViewMeetingEventItem -> MEETING_EVENT_TYPE
            is StartViewItem.ShowAllItemsBtn -> SHOW_ALL_ITEMS_BTN_TYPE
            is StartViewItem.MyEventsList -> MY_EVENTS_LIST_TYPE
            is StartViewItem.EmptyStartViewMeetingEventItem -> EMPTY_MEETING_EVENT_TYPE
            is StartViewItem.EmptyMyEventsList -> EMPTY_MY_EVENT_LIST_TYPE
            is StartViewItem.ShowAllItemsTextBtn -> SHOW_ALL_ITEMS_TEXT_BTN_TYPE
        }
    }

    fun updateListData(list: List<StartViewItem>) {
        startViewItemList = list
        notifyDataSetChanged()
    }

    companion object {
        private const val SECTION_HEADER_TYPE = 1
        private const val DATE_HEADER_TYPE = 2
        private const val MEETING_EVENT_TYPE = 3
        private const val SHOW_ALL_ITEMS_BTN_TYPE = 4
        private const val MY_EVENTS_LIST_TYPE = 5
        private const val EMPTY_MY_EVENT_LIST_TYPE = 6
        private const val EMPTY_MEETING_EVENT_TYPE = 7
        private const val SHOW_ALL_ITEMS_TEXT_BTN_TYPE = 8

    }

}