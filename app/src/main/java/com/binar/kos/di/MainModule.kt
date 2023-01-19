package com.binar.kos.di

import android.app.Application
import com.binar.kos.viewmodel.LoginViewModel
import com.binar.kos.viewmodel.MainViewModel
import com.binar.kos.viewmodel.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


object MainModule {
    val Application.mainModule get() = module {
        viewModel { MainViewModel(get()) }
        viewModel { RegisterViewModel(get()) }
        viewModel { LoginViewModel(get()) }
    }
}
