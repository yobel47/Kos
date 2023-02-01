package com.binar.kos.di

import android.app.Application
import com.binar.kos.data.repository.LoginRepository
import com.binar.kos.data.repository.MainRepository
import com.binar.kos.data.repository.RegisterRepository
import com.binar.kos.data.repository.RoomRepository
import com.binar.kos.network.ApiClient
import com.binar.kos.network.ApiHelper
import org.koin.dsl.module


object DataModule {
    val Application.dataModule get() = module {

        //DATABASE

        //API SERVICE
        single { ApiClient.instance }
        single { ApiHelper(get()) }

        //REPOSITORY
        factory { MainRepository(get()) }
        factory { RegisterRepository(get()) }
        factory { LoginRepository(get()) }
        factory { RoomRepository(get()) }
    }
}
