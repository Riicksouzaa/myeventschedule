package com.rsdev.myeventschedule.api

import com.rsdev.myeventschedule.model.events.Events
import retrofit2.http.GET

interface EventsApi {
    @GET("api/events")
    suspend fun getEvents(): List<Events>
}