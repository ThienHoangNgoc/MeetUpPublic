package com.example.meetupapp.app_extensions

import java.text.SimpleDateFormat
import java.util.*

object UtilDateTime {


     fun getLocalTimeStamp(): String {
        val format = SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSSz", Locale.getDefault())
        return format.format(Calendar.getInstance().time)
    }

    fun getLocalDate(): String {
        val format = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return format.format(Calendar.getInstance().time)
    }

}