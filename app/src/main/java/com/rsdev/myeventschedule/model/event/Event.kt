package com.rsdev.myeventschedule.model.event

import com.rsdev.myeventschedule.utils.highorder.formatToDate
import java.io.Serializable

data class Event(
    val id: Long = 0,
    val date: Long = 0,
    val description: String = "",
    val image: String = "",
    val longitude: String = "",
    val latitude: String = "",
    val price: Double = 0.0,
    val title: String = ""
) : Serializable {

    fun getMonth(): String = date.formatToDate("MMM").uppercase().take(3)

    fun getDay(): String = date.formatToDate("dd")
}
