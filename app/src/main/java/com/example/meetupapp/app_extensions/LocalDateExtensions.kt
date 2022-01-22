package com.example.meetupapp.app_extensions

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun String.convertDate(): String {
    val localDate = this.convertStringToLocalDate()
    val formatter = DateTimeFormatter.ofPattern("EE, dd-MMM")
    val dateString = localDate.format(formatter)
    return dateString.replace(".","").replace("-",".")
}


fun String.convertStringToLocalDate(): LocalDate {
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMANY)
    return LocalDate.parse(this, formatter)
}

fun String.convertStringToLocalDateTime(): LocalDateTime {
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMANY)
    return LocalDateTime.parse(this, formatter)
}

fun String.convertStringToDateComparable(): Date{
    val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return simpleDateFormat.parse(this)
}





