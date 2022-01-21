package com.example.meetupapp.ui.start.adapter.viewholder

import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.meetupapp.databinding.ShowAllItemsBtnBinding
import com.example.meetupapp.ui.start.StartFragmentClickHandler

class ShowAllItemsBtnViewHolder(val binding: ShowAllItemsBtnBinding) : RecyclerView.ViewHolder(binding.root) {

    fun initLayout(holder: ShowAllItemsBtnViewHolder, position: Int, startFragment: Fragment) {
        holder.binding.showAllItemBtnBig.setOnClickListener(View.OnClickListener {
            (startFragment as StartFragmentClickHandler).onShowAllOpenEventsClicked()
        })
    }

}