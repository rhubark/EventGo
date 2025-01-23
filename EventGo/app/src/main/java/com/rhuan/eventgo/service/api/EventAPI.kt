package com.rhuan.eventgo.service.api

import com.rhuan.eventgo.domain.request.User
import com.rhuan.eventgo.domain.response.Event
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EventAPI {

    @GET("events")
    suspend fun getAllEvents()
    : Response<List<Event>>

    @GET("events/{id}")
    suspend fun getEvent(@Path("id") id: String)
    : Response<Event>

    @POST("checkin")
    suspend fun setCheckIn(@Body userId: User)
    : Response<ResponseBody>

}