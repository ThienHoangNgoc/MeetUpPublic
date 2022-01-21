package com.example.meetupapp.ui.start.adapter.viewholder

import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.meetupapp.databinding.ShowAllItensTextBtnBinding
import com.example.meetupapp.models.StartViewItem
import com.example.meetupapp.ui.start.StartFragmentClickHandler

class ShowAllItemsTextBtnViewHolder(val binding: ShowAllItensTextBtnBinding) : RecyclerView.ViewHolder(binding.root) {

    fun initLayout(
        holder: ShowAllItemsTextBtnViewHolder,
        position: Int,
        startViewItemList: List<StartViewItem>,
        startFragment: Fragment
    ) {
        val item = startViewItemList[position] as StartViewItem.ShowAllItemsTextBtn
        with(holder.binding) {
            if (item.showTextBtn) {
                showAllItemBtnSmall.visibility = View.VISIBLE
                showAllItemBtnSmall.setOnClickListener(View.OnClickListener {
                    if(item.isMyEvents){
                        (startFragment as StartFragmentClickHandler).onShowAllMyEventsClicked()
                    }else{
                        (startFragment as StartFragmentClickHandler).onShowAllOpenEventsClicked()
                    }
                })
            }

        }
    }

}