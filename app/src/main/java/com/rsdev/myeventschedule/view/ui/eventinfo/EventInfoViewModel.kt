package com.rsdev.myeventschedule.view.ui.eventinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rsdev.myeventschedule.api.EventsApiState
import com.rsdev.myeventschedule.api.RetrofitService
import com.rsdev.myeventschedule.api.Status
import com.rsdev.myeventschedule.data.EventsDataSource
import com.rsdev.myeventschedule.data.EventsRepository
import com.rsdev.myeventschedule.model.event.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EventInfoViewModel() :
    ViewModel() {
    private val eventsRepository = EventsRepository(EventsDataSource(RetrofitService.create()))
    val eventState = MutableStateFlow(EventsApiState(Status.LOADING, mutableListOf(Event()), null))
    init {
        getAllEvents()
    }

    private fun getAllEvents() {
        eventState.update { EventsApiState.loading() }
        viewModelScope.launch {
            eventsRepository.allEvent
                .catch {
                    eventState.update { EventsApiState.error(it.message.toString()) }
                }
                .collect {
                    it.data?.toMutableList()?.let { events ->
                        eventState.update { EventsApiState.success(events) }
                    } ?: kotlin.run {
                        eventState.update {
                            EventsApiState.error("Não foi encontrado nenhum resultado")
                        }
                    }
                }
        }
    }
}