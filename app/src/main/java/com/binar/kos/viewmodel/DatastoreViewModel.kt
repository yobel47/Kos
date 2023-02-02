package com.binar.kos.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.binar.kos.utils.DatastoreManager
import kotlinx.coroutines.launch

class DatastoreViewModel (private val pref: DatastoreManager) : ViewModel() {

    fun saveLoginState(value: Boolean) {
        viewModelScope.launch {
            pref.saveLoginStateToDataStore(value)
        }
    }

    fun getLoginState(): LiveData<Boolean> {
        return pref.readLoginStateFromDataStore().asLiveData()
    }

    fun saveAccessToken(value: String) {
        viewModelScope.launch {
            pref.saveAccessTokenToDataStore(value)
        }
    }

    fun getAccessToken(): LiveData<String> {
        return pref.readAccessTokenFromDataStore().asLiveData()
    }

    fun deleteAllData() {
        viewModelScope.launch {
            pref.removeFromDataStore()
        }
    }
}