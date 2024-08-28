package com.samy.e_ramo.presentation

import androidx.lifecycle.viewModelScope
import com.samy.e_ramo.data.datasorce.ApiService
import com.samy.e_ramo.utils.BaseViewModel
import com.samy.e_ramo.utils.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val repository: ProductRepository) :
    BaseViewModel() {

    private val _dataStateFlow = MutableStateFlow<NetworkState>(NetworkState.Idle)
    val dataStateFlow get() = _dataStateFlow


    private var dataLoaded = false
    fun fetchData() {
        if (dataLoaded) return

        _dataStateFlow.value = NetworkState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            runApi(
                _dataStateFlow,
                repository.getData("mobile")
            )
            dataLoaded = true
        }

    }

}

class ProductRepository @Inject constructor(private val services: ApiService) {
    suspend fun getData(
        platform: String,
    ) =
        services.getData(platform)

}