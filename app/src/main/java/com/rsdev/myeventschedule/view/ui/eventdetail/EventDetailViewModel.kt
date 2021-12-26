package com.rsdev.myeventschedule.view.ui.eventdetail

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

class EventDetailViewModel : ViewModel() {
    private val eventsRepository = EventsRepository(EventsDataSource(RetrofitService.create()))
    val eventState = MutableStateFlow(EventsApiState(Status.LOADING, Event(), null))
    var checkInState = MutableStateFlow(EventsApiState(Status.NOTHING, Event(), null))

    fun getEventById(id: Int) {
        viewModelScope.launch {
            eventsRepository.getEventById(id)
                .catch { eventState.value = EventsApiState.error(it.message.toString()) }
                .collect {
                    it.data?.let { event ->
                        eventState.value = EventsApiState.success(event)
                    } ?: kotlin.run {
                        eventState.value =
                            EventsApiState.error("Não foi encontrado detalhe com id [$id]")
                    }
                }
        }
    }

    fun checkIn(event: Event, name: String, email: String) {
        viewModelScope.launch {
            checkInState = MutableStateFlow(
                EventsApiState(
                    Status.LOADING,
                    Event(),
                    "Aguarde, estamos realizando seu check-in."
                )
            )
            eventsRepository.sendCheckIn(event, name, email)
                .catch {
                    checkInState.update { EventsApiState.error(it.message.toString()) }
                }
                .collect {
                    it.data?.let { event ->
                        if (event.isSuccessful) {
//                            checkInState.update {  }
                            checkInState.update { EventsApiState.success(Event()) }
                        } else {
                            checkInState.update {
                                event.errorBody()?.string()?.let { errorMessage ->
                                    EventsApiState.error(errorMessage)
                                } ?: kotlin.run {
                                    EventsApiState.error("Houve um erro ao realizar o check-in")
                                }
                            }
                        }
                    }
                }
        }
    }
}