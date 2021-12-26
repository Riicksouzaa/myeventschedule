package com.rsdev.myeventschedule.data

import com.rsdev.myeventschedule.api.EventsApiState
import com.rsdev.myeventschedule.model.event.Event
import kotlinx.coroutines.flow.Flow

class EventsRepository(private val eventsDataSource: EventsDataSource) {
    val allEvent: Flow<EventsApiState<List<Event>>> = eventsDataSource.events

    fun getEventById(id: Int) = eventsDataSource.getEventById(id)
    fun sendCheckIn(event: Event?, name: String, email: String) =
        eventsDataSource.sendCheckIn(event, name, email)
}