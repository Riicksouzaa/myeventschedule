package com.rsdev.myeventschedule.api

import com.rsdev.myeventschedule.model.event.Event
import retrofit2.Response
import retrofit2.http.*

interface EventsApi {
    @GET("api/events")
    suspend fun getEvents(): List<Event>

    @GET("api/events/{id}")
    suspend fun getEventById(@Path("id") id: Int): Event

    @FormUrlEncoded
    @POST("api/checkin")
    suspend fun sendCheckIn(
        @Field("eventId") eventId: Int,
        @Field("name") name: String,
        @Field("email") email: String
    ): Response<Event>
}