package com.binar.kos.di

import android.app.Application
import com.binar.kos.data.repository.LoginRepository
import com.binar.kos.data.repository.MainRepository
import com.binar.kos.data.repository.RegisterRepository
import com.binar.kos.data.repository.SearchRepository
import com.binar.kos.network.ApiClient
import com.binar.kos.network.ApiHelper
import com.binar.kos.network2.ApiClient2
import com.binar.kos.network2.ApiHelper2
import org.koin.dsl.module


object DataModule {
    val Application.dataModule get() = module {

        //DATABASE

        //API SERVICE
        single { ApiClient.instance }
        single { ApiHelper(get()) }
        single { ApiClient2.instance }
        single { ApiHelper2(get()) }

        //REPOSITORY
        factory { MainRepository(get()) }
        factory { RegisterRepository(get()) }
        factory { LoginRepository(get()) }
        factory { SearchRepository(get()) }
    }
}
