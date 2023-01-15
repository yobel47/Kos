package com.binar.kos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.binar.kos.data.repository.MainRepository
import com.binar.kos.data.repository.RegisterRepository
import com.binar.kos.network.ApiHelper

class ViewModelFactory private constructor(private val apiHelper: ApiHelper) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(MainRepository(apiHelper)) as T
        } else if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(RegisterRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}