package com.binar.kos

import android.app.Application
import com.binar.kos.di.DataModule.dataModule
import com.binar.kos.di.MainModule.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(listOf(
                dataModule,
                mainModule
            ))
        }
    }
}
