package com.rhuan.eventgo.di

import com.rhuan.eventgo.domain.usecase.EventsRepository
import com.rhuan.eventgo.domain.usecase.EventsRepositoryImpl
import com.rhuan.eventgo.service.RetrofitProvider
import com.rhuan.eventgo.ui.viewModels.EventFragmentViewModel
import com.rhuan.eventgo.ui.viewModels.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    factory { RetrofitProvider() }

    single<EventsRepository> { EventsRepositoryImpl(get()) }

    viewModel { HomeViewModel(get())}
    viewModel { EventFragmentViewModel(get()) }

}