package com.rsdev.myeventschedule.api

data class EventsApiState<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T): EventsApiState<T> = EventsApiState(Status.SUCCESS, data, null)
        fun <T> error(message: String): EventsApiState<T> =
            EventsApiState(Status.ERROR, null, message)

        fun <T> loading(): EventsApiState<T> = EventsApiState(Status.LOADING, null, null)
        fun <T> nothing(): EventsApiState<T> = EventsApiState(Status.NOTHING, null, null)
    }
}

enum class Status {
    LOADING,
    SUCCESS,
    ERROR,
    NOTHING
}