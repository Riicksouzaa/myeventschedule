package com.rsdev.myeventschedule.model.events

import java.math.BigInteger

data class Events(
    val id: Long = 0,
    val date: BigInteger = BigInteger("0"),
    val descripton: String = "",
    val image: String = "",
    val longitude: String = "",
    val latitude: String = "",
    val price: Double = 0.0,
    val title: String = ""
)
