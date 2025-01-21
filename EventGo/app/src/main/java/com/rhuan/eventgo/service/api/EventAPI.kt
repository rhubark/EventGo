package com.rhuan.eventgo.service.api

import com.rhuan.eventgo.domain.response.Event
import retrofit2.Call
import retrofit2.http.GET

interface EventAPI {

    @GET("events")
    fun getAllEvents(): Call<List<Event>>
}