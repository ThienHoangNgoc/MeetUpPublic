package com.example.meetupapp.ui.start.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.meetupapp.databinding.SectionHeaderItemBinding
import com.example.meetupapp.models.StartViewItem

class SectionHeaderViewHolder(val binding: SectionHeaderItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(sectionHeaderItem: StartViewItem.SectionHeaderItem) {
        binding.sectionHeaderText.text = itemView.context.getString(sectionHeaderItem.headerText)
    }

}
