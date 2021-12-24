package com.rsdev.myeventschedule.view.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rsdev.myeventschedule.api.EventsApiState
import com.rsdev.myeventschedule.api.RetrofitService
import com.rsdev.myeventschedule.api.Status
import com.rsdev.myeventschedule.data.EventsDataSource
import com.rsdev.myeventschedule.data.EventsRepository
import com.rsdev.myeventschedule.model.events.Events
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class EventsViewModel() :
    ViewModel() {
    private val eventsRepository = EventsRepository(EventsDataSource(RetrofitService.create()))

    val eventState = MutableStateFlow(EventsApiState(Status.LOADING, mutableListOf(Events()), null))

    init {
        getAllEvents()
    }

    private fun getAllEvents() {
        eventState.value = EventsApiState.loading()
        viewModelScope.launch {
            eventsRepository.allEvents
                .catch {
                    eventState.value = EventsApiState.error(it.message.toString())
                }
                .collect {
                    it.data?.toMutableList()?.let { events ->
                        eventState.value = EventsApiState.success(events)
                    } ?: kotlin.run {
                        eventState.value =
                            EventsApiState.error("Não foi encontrado nenhum resultado")
                    }
                }
        }
    }
}