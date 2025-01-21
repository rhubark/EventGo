package com.rhuan.eventgo.domain.response

import com.rhuan.eventgo.domain.request.User


data class Event(
    val id: String,
    val title: String,
    val image: String,
    val description: String,
    val date: Long,
    val price: Double,
    val latitude: Double,
    val longitude: Double,
    val people: List<User>

)
