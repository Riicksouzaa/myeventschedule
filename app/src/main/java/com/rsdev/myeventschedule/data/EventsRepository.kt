package com.rsdev.myeventschedule.data

import com.rsdev.myeventschedule.api.EventsApiState
import com.rsdev.myeventschedule.model.events.Events
import kotlinx.coroutines.flow.Flow

class EventsRepository(eventsDataSource: EventsDataSource) {
    val allEvents: Flow<EventsApiState<List<Events>>> = eventsDataSource.events
}