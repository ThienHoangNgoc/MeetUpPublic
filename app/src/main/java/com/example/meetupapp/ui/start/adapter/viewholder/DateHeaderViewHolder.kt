package com.example.meetupapp.ui.start.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.meetupapp.databinding.DateHeaderItemBinding
import com.example.meetupapp.models.StartViewItem

class DateHeaderViewHolder(val binding: DateHeaderItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun initLayout(holder: DateHeaderViewHolder, position: Int, startViewItemList: List<StartViewItem>) {
        val item = startViewItemList[position] as StartViewItem.DateHeaderItem
        with(holder.binding) {
            dateHeaderText.text = item.headerText
        }
    }
}