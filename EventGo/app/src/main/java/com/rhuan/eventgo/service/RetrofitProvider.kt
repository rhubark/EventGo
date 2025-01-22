package com.rhuan.eventgo.service

import com.rhuan.eventgo.service.api.EventAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitProvider {

    companion object {
        private const val BASE_URL = "https://5f5a8f24d44d640016169133.mockapi.io/api/"
    }

    fun provideEventService(): EventAPI {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(EventAPI::class.java)
    }


}