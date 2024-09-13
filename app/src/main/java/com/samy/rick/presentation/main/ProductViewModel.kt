package com.samy.rick.presentation.main

import androidx.lifecycle.viewModelScope
import com.samy.rick.utils.BaseViewModel
//import com.samy.rick.data.datasorce.ApiService
//import com.samy.rick.utils.NetworkState
import com.samy.rick.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor
    (
//    private val repository: ProductRepository
) : BaseViewModel() {
//
//    private val _dataStateFlow = MutableStateFlow<NetworkState>(NetworkState.Idle)
//    val dataStateFlow get() = _dataStateFlow
//
//
//    private var dataLoaded = false
//    fun fetchData() {
//        if (dataLoaded) return
//
//        _dataStateFlow.value = NetworkState.Loading
//        if (Utils.isInternetAvailable()) {
//
//            viewModelScope.launch(Dispatchers.IO) {
//                runApi(
//                    _dataStateFlow,
//                    repository.getData("mobile")
//                )
//                dataLoaded = true
//            }
//
//
//        } else
//            _dataStateFlow.value = NetworkState.Error("Check Internet Connection")
//
//    }
//    }
//
//    class ProductRepository @Inject constructor(private val services: ApiService) {
//        suspend fun getData(
//            platform: String,
//        ) =
//            services.getData(platform)

}