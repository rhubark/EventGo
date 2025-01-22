package com.rhuan.eventgo

import android.app.Application
import com.rhuan.eventgo.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class EventGoApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@EventGoApp)

            modules(appModule)
        }
    }
}