package com.rhuan.eventgo.ui.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rhuan.eventgo.data.repository.EventsRepository
import com.rhuan.eventgo.domain.request.User
import com.rhuan.eventgo.domain.response.Event
import kotlinx.coroutines.launch

class EventFragmentViewModel(
    private val eventsRepository: EventsRepository
) : ViewModel() {

    val loadingEvent: MutableLiveData<Boolean> = MutableLiveData(true)
    val fetchEventResult: MutableLiveData<Event> = MutableLiveData()
    val fetchEventDataError: MutableLiveData<Boolean> = MutableLiveData(false)

    val postSuccess: MutableLiveData<Boolean> = MutableLiveData(false)
    val postError: MutableLiveData<Boolean> = MutableLiveData(false)

    fun getEvent(id: String) {
        viewModelScope.launch {
            val response = eventsRepository.getEvent(id)
            if (response.isSuccessful) {
                loadingEvent.value = false
                fetchEventResult.value = response.body()
            } else {
                loadingEvent.value = false
                fetchEventDataError.value = true
                Log.e("ERROR", response.message())
            }
        }
    }

    fun setCheckIn(userId: User) {
        viewModelScope.launch {
            loadingEvent.value = true
            val response = eventsRepository.setCheckIn(userId)
            if (response.isSuccessful) {
                loadingEvent.value = false
                postSuccess.value = true
            } else {
                loadingEvent.value = false
                postError.value = true
                Log.e("ERROR", response.message())
            }
        }
    }
}