package com.example.meetupapp.models

import android.content.Context
import com.example.meetupapp.R
import com.example.meetupapp.app_extensions.getColorFromRes

enum class EventCategory(var checked: Boolean) {
    SPORT(false),
    SOCIAL(false),
    ONLINE(false),
    GENERAL(false),
}

fun EventCategory.isChecked(){
    when(this){
        EventCategory.SPORT -> {
            EventCategory.SPORT.checked = true
            EventCategory.SOCIAL.checked = false
            EventCategory.ONLINE.checked = false
            EventCategory.GENERAL.checked = false
        }
        EventCategory.SOCIAL -> {
            EventCategory.SOCIAL.checked = true
            EventCategory.SPORT.checked = false
            EventCategory.ONLINE.checked = false
            EventCategory.GENERAL.checked = false
        }
        EventCategory.ONLINE -> {
            EventCategory.ONLINE.checked = true
            EventCategory.SPORT.checked = false
            EventCategory.SOCIAL.checked = false
            EventCategory.GENERAL.checked = false
        }
        EventCategory.GENERAL -> {
            EventCategory.GENERAL.checked = true
            EventCategory.SPORT.checked = false
            EventCategory.SOCIAL.checked = false
            EventCategory.ONLINE.checked = false
        }

    }

}

fun resetStates() {
    EventCategory.GENERAL.checked = false
    EventCategory.SPORT.checked = false
    EventCategory.SOCIAL.checked = false
    EventCategory.ONLINE.checked = false
}


fun EventCategory.getColor(context: Context):Int{
    return when(this){
        EventCategory.SPORT -> context.getColorFromRes(R.color.sport_category_color)
        EventCategory.SOCIAL -> context.getColorFromRes(R.color.social_category_color)
        EventCategory.ONLINE -> context.getColorFromRes(R.color.online_category_color)
        EventCategory.GENERAL -> context.getColorFromRes(R.color.general_category_color)
    }
}

fun EventCategory.getCategoryType(context: Context):String{
    return when(this){
        EventCategory.SPORT -> context.getString(R.string.event_category_sport)
        EventCategory.SOCIAL -> context.getString(R.string.event_category_social)
        EventCategory.ONLINE -> context.getString(R.string.event_category_online)
        EventCategory.GENERAL -> context.getString(R.string.event_category_general)
    }
}