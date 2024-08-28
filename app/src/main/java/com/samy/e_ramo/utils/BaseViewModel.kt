package com.samy.e_ramo.utils

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
open class BaseViewModel @Inject constructor() :
    ViewModel() {

    fun <T> runApi(
        _apiStateFlow: MutableStateFlow<NetworkState>,
        block: Response<T>
    ) {

        _apiStateFlow.value = NetworkState.Loading
        try {
            if (Utils.isInternetAvailable())
                CoroutineScope(Dispatchers.IO).launch {

                    kotlin.runCatching {
                        block
                    }.onFailure {e->
                        _apiStateFlow.value = NetworkState.Error(e.message)
                    }.onSuccess {
                        Log.e(TAG, "runApi: onSuccess")
                        if (it.body() != null)
                            _apiStateFlow.value = NetworkState.Result(it.body())
                        else {
                            Log.e(TAG, "runApi: ${it.errorBody()}")
                            _apiStateFlow.value = NetworkState.Error("${it.errorBody()}")
                        }
                    }

                }
            else
                _apiStateFlow.value = NetworkState.Error("Chick Internet Connection")
        } catch (e: Exception) {
            Log.e(TAG, "runApi: ${e.message}")
        }


    }

    companion object {
        private val TAG = this::class.java.name


    }

}