package com.rhuan.eventgo.di

import com.rhuan.eventgo.data.repository.EventsRepository
import com.rhuan.eventgo.data.repository.EventsRepositoryImpl
import com.rhuan.eventgo.service.RetrofitProvider
import com.rhuan.eventgo.ui.viewholders.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    factory { RetrofitProvider() }

    single<EventsRepository> { EventsRepositoryImpl(get())}

    viewModel { HomeViewModel(get())}

}