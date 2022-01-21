package com.example.meetupapp.utils

import android.annotation.SuppressLint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateUtils {

    companion object{
        fun getCurrentDate(): String{
            val date = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("MMMM, d")
            return date.format(formatter)
        }

    }


}