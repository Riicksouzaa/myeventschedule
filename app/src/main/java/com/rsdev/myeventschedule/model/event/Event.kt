package com.rsdev.myeventschedule.model.event

import java.text.SimpleDateFormat
import java.util.*

data class Event(
    val id: Long = 0,
    val date: Long = 0,
    val description: String = "",
    val image: String = "",
    val longitude: String = "",
    val latitude: String = "",
    val price: Double = 0.0,
    val title: String = ""
) {

    fun getMonth(): String = try {
        val sdf = SimpleDateFormat("MMM")
        val netDate = Date(date)
        sdf.format(netDate).uppercase().take(3)
    } catch (e: Exception) {
        e.toString()
    }

    fun getDay(): String = try {
        val sdf = SimpleDateFormat("dd")
        val netDate = Date(date)
        sdf.format(netDate)
    } catch (e: Exception) {
        e.toString()
    }

}
