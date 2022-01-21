package com.example.meetupapp.ui.start.adapter.viewholder

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.meetupapp.R
import com.example.meetupapp.app_extensions.changeBackground
import com.example.meetupapp.databinding.MeetingEventItemBinding
import com.example.meetupapp.models.StartViewItem
import com.example.meetupapp.models.User
import com.example.meetupapp.models.getCategoryType
import com.example.meetupapp.models.getColor

class MeetingEventViewHolder(val binding: MeetingEventItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun initLayout(holder: MeetingEventViewHolder, position: Int, startViewItemList: List<StartViewItem>) {
        val item = (startViewItemList[position] as StartViewItem.StartViewMeetingEventItem).meetingEventItem
        val context = holder.binding.root.context
        with(holder.binding) {
            with(item) {
                categoryIndicator.setBackgroundColor(category.getColor(context))
                meetingEventItemTitle.text = item.title
                meetingEventItemStartDate.text = item.startDate
                meetingEventItemLocation.text = item.location
                meetingEventItemStartTime.text = item.startTime
                //Todo: change int to drawable later when db is implemented
                organizerProfileImage.setImageDrawable(root.context.getDrawable(organizer.profileImage))
                meetingEventItemOrganizer.text = organizer.name
                meetingEventItemCategoryLabel.text = item.category.getCategoryType(context)
                meetingEventItemCategoryLabel.setBackgroundColor(item.category.getColor(context))
                meetingEventItemLayout.strokeColor = item.category.getColor(context)
                //Todo: participants number isn't right
                meetingEventMaxParticipants.visibility = View.VISIBLE
                if (item.maxParticipants > 0) {
                    val maxText = (item.participants.size).toString() + "/" + item.maxParticipants
                    meetingEventMaxParticipants.text = maxText
                }else{
                    meetingEventMaxParticipants.text = item.participants.size.toString()
                }
                val participantsList = item.participants
                if(participantsList.isNotEmpty()){
                    setUpParticipantImage(participantImageView01,holder,participantsList, View.VISIBLE)
                }
                if(participantsList.size >= 2){
                    setUpParticipantImage(participantImageView02,holder,participantsList,View.VISIBLE)
                }
                if(participantsList.size >= 3){
                    setUpParticipantImage(participantImageView03,holder,participantsList,View.VISIBLE)
                }
                if(participantsList.size >= 4){
                    setUpParticipantImage(participantImageView03,holder,participantsList,View.GONE)
                    binding.notShownParticipants.visibility = View.VISIBLE
                    val displayText = "+" + (participantsList.size - 2)
                    binding.notShownParticipantsTextNumber.text = displayText
                    val color = item.category.getColor(binding.root.context)
                    val backgroundDrawable: Drawable = binding.notShownParticipantsBackground.background
                    backgroundDrawable.changeBackground(color)
                }
            }

        }

    }

    private fun setUpParticipantImage(imageView: ImageView, holder: MeetingEventViewHolder, list : List<User>, visibility: Int){
        //Todo: Replace with setDrawable when db is available
        imageView.setImageDrawable(ResourcesCompat.getDrawable(holder.binding.root.context.resources, list[0].profileImage, null))
        imageView.visibility = visibility
    }

}