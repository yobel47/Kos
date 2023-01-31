package com.binar.kos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.binar.kos.data.repository.LoginRepository
import com.binar.kos.data.repository.MainRepository
import com.binar.kos.data.repository.RegisterRepository
import com.binar.kos.data.repository.SearchRepository
import com.binar.kos.network2.ApiHelper2

class ViewModelFactory2 private constructor(private val apiHelper2: ApiHelper2) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(SearchRepository(apiHelper2)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}