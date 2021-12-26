package com.rsdev.myeventschedule.data

import com.rsdev.myeventschedule.api.EventsApi
import com.rsdev.myeventschedule.api.EventsApiState
import com.rsdev.myeventschedule.model.event.Event
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class EventsDataSource(
    private val eventsApi: EventsApi,
    private val refreshInterval: Long = 10000
) {
    val events = flow {
        val lastEvents = eventsApi.getEvents()
        emit(EventsApiState.success(lastEvents))
        delay(refreshInterval)
    }

    fun getEventById(id: Int) = flow {
        val getEvent = eventsApi.getEventById(id)
        emit(EventsApiState.success(getEvent))
        delay(refreshInterval)
    }

    fun sendCheckIn(event: Event?, name: String, email: String) = flow {
        event?.let {
            val checkIn = eventsApi.sendCheckIn(it.id.toInt(), name, email)
            emit(EventsApiState.success(checkIn))
            delay(refreshInterval)
        }
    }
}