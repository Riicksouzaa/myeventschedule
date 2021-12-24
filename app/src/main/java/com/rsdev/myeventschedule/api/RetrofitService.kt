package com.rsdev.myeventschedule.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

interface RetrofitService {
    companion object {
        fun create(): EventsApi =
            Retrofit.Builder()
                .baseUrl("https://5f5a8f24d44d640016169133.mockapi.io/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(EventsApi::class.java)
    }
}