package com.rsdev.myeventschedule.data

import com.rsdev.myeventschedule.api.EventsApi
import com.rsdev.myeventschedule.api.EventsApiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class EventsDataSource(
    private val eventsApi: EventsApi,
    private val refreshInterval: Long = 5000
) {
    val events = flow {
        val lastEvents = eventsApi.getEvents()
        emit(EventsApiState.success(lastEvents))
        delay(refreshInterval)
    }
}