package com.rhuan.eventgo.ui.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rhuan.eventgo.domain.request.User
import com.rhuan.eventgo.domain.response.Event
import com.rhuan.eventgo.domain.usecase.EventsRepository
import kotlinx.coroutines.launch

class EventFragmentViewModel(
    private val eventsRepository: EventsRepository
) : ViewModel() {

    private val loadingEvent: MutableLiveData<Boolean> = MutableLiveData(true)
    private val fetchEventResult: MutableLiveData<Event> = MutableLiveData()

    private val postSuccess: MutableLiveData<Boolean> = MutableLiveData(false)
    val postError: MutableLiveData<Boolean> = MutableLiveData(false)

    fun getEvent(id: String) {
        viewModelScope.launch {
            Log.i("ID33","asssss")
            val response = eventsRepository.getEvent(id)

            if (response.isSuccessful) {
                loadingEvent.value = false
                fetchEventResult.value = response.body()
            } else {
                loadingEvent.value = false
                Log.e("ERROR", response.message())
            }
        }
    }

    fun setCheckIn(userId: User) {
        viewModelScope.launch {

            eventsRepository.setCheckIn(userId)
        }
    }

    fun fetchEventResult() = fetchEventResult as MutableLiveData<Event>

    fun postSuccess() = postSuccess as MutableLiveData<Boolean>
    fun postError() = postError as MutableLiveData<Boolean>
}