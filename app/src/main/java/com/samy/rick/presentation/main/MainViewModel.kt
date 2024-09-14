package com.samy.rick.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.samy.rick.domain.usecase.GetCharactersUsecase
import com.samy.rick.pojo.CharacterResponse
import com.samy.rick.pojo.CharacterResult
import com.samy.rick.utils.BaseViewModel
import com.samy.rick.utils.DataState
//import com.samy.rick.data.datasorce.ApiService
//import com.samy.rick.utils.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor
    (
    private val getCharactersUseCase: GetCharactersUsecase
) : ViewModel() {

    private val _uiState = MutableStateFlow<DataState<CharacterResponse>>(DataState.Loading)
    val uiState: MutableStateFlow<DataState<CharacterResponse>> = _uiState

    init {
        fetchCharacters()
    }

    fun fetchCharacters() {
        _uiState.value = DataState.Loading
            viewModelScope.launch {
                try {
                getCharactersUseCase().collect {
                    _uiState.value = it
                }
                } catch (e: Exception) {
                    _uiState.value = DataState.Error(e.message ?: "Unknown error")
                }
            }
    }

}