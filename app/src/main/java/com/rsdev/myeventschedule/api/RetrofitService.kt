package com.rsdev.myeventschedule.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "http://5f5a8f24d44d640016169133.mockapi.io"

interface RetrofitService {
    companion object {
        fun create(): EventsApi = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(EventsApi::class.java)
    }
}