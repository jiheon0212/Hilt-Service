package com.example.dihiltlibrary

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Singleton

@Singleton
object GetTime {
    fun getCurrentDay(): String {
        val currentDate = Date()
        val currentZone = TimeZone.getTimeZone("Asia/Seoul")
        val dayFormat = SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA)

        dayFormat.timeZone = currentZone
        val dayValue = dayFormat.format(currentDate)

        return dayValue
    }
    fun getCurrentTime(): String {
        val currentDate = Date()
        val currentZone = TimeZone.getTimeZone("Asia/Seoul")
        val timeFormat = SimpleDateFormat("a hh:mm", Locale.KOREA)
        timeFormat.timeZone = currentZone
        val timeValue = timeFormat.format(currentDate)

        return timeValue
    }
}