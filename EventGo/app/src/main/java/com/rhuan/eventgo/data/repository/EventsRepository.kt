package com.rhuan.eventgo.data.repository

import com.rhuan.eventgo.domain.response.Event
import com.rhuan.eventgo.service.RetrofitProvider
import retrofit2.Response

interface EventsRepository {
    suspend fun getAllEvents(): Response<List<Event>>
    suspend fun getEvent(id: String): Response<Event>
}

class EventsRepositoryImpl(
    private val eventsApi: RetrofitProvider
) : EventsRepository {

    override suspend fun getAllEvents(): Response<List<Event>> =
        eventsApi.provideEventService().getAllEvents()

    override suspend fun getEvent(id: String): Response<Event> =
        eventsApi.provideEventService().getEvent(id)

}