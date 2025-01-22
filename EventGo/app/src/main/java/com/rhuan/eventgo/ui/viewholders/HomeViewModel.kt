package com.rhuan.eventgo.ui.viewholders

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rhuan.eventgo.data.repository.EventsRepository
import com.rhuan.eventgo.domain.response.Event
import kotlinx.coroutines.launch

class HomeViewModel(
    private val eventsRepository: EventsRepository
) : ViewModel() {

    val events: MutableLiveData<List<Event>> = MutableLiveData()

//    private val fetchEventsResult = MediatorLiveData<Result<Event>>()
//    private val fetchEventDataSuccessful = MediatorLiveData<Event>()
//    private val fetchEventDataError = MediatorLiveData<String>()


    val loadingEvent: MutableLiveData<Boolean> = MutableLiveData(true)
    val fetchEventsResult: MutableLiveData<List<Event>> = MutableLiveData()
    val fetchEventDataError: MutableLiveData<Boolean> = MutableLiveData(false)

    fun getAllEvents() {
        viewModelScope.launch {
            val response = eventsRepository.getAllEvents()
            if (response.isSuccessful) {
                loadingEvent.value = false
                fetchEventsResult.value = response.body()
            } else {
                loadingEvent.value = false
                fetchEventDataError.value = true
                Log.e("ERROR", response.message())
            }
        }
    }
}