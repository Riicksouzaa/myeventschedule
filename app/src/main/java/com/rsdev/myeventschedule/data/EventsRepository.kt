package com.rsdev.myeventschedule.data

import com.rsdev.myeventschedule.api.EventsApiState
import com.rsdev.myeventschedule.model.event.Event
import kotlinx.coroutines.flow.Flow

class EventsRepository(eventsDataSource: EventsDataSource) {
    val allEvent: Flow<EventsApiState<List<Event>>> = eventsDataSource.events
}