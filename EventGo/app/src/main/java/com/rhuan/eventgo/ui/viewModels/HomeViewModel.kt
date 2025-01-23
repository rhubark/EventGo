package com.rhuan.eventgo.ui.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rhuan.eventgo.domain.response.Event
import com.rhuan.eventgo.domain.usecase.EventsRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val eventsRepository: EventsRepository
) : ViewModel() {

    val loadingAllEvents: MutableLiveData<Boolean> = MutableLiveData(true)
    val fetchAllEventsResult: MutableLiveData<List<Event>> = MutableLiveData()
    val fetchAllEventsDataError: MutableLiveData<Boolean> = MutableLiveData(false)

    fun getAllEvents() {
        viewModelScope.launch {
            val response = eventsRepository.getAllEvents()
            if (response.isSuccessful) {
                loadingAllEvents.value = false
                fetchAllEventsResult.value = response.body()
            } else {
                loadingAllEvents.value = false
                fetchAllEventsDataError.value = true
                Log.e("ERROR", response.message())
            }
        }
    }
}