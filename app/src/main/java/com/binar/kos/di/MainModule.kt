package com.binar.kos.di

import android.app.Application
import com.binar.kos.viewmodel.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object MainModule {
    val Application.mainModule get() = module {
        viewModel { MainViewModel(get()) }
        viewModel { RegisterViewModel(get()) }
        viewModel { LoginViewModel(get()) }
        viewModel { SearchViewModel(get()) }
        viewModel { DatastoreViewModel(get()) }
        viewModel { HomeViewModel(get()) }
        viewModel { LogoutViewModel(get()) }
        viewModel { RoomViewModel(get()) }
    }
}
