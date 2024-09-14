package com.samy.rick.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.firebase.auth.FirebaseUser
import com.samy.rick.data.datasorce.AuthDataSource
import com.samy.rick.data.datasorce.CharacterServices
import com.samy.rick.pojo.CharacterResponse
import com.samy.rick.pojo.CharacterResult
import com.samy.rick.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class CharacterRepository(private val services: CharacterServices) {

    fun getCharacterPagingData(): Flow<DataState<CharacterResponse>> = flow {
        try {
            val response = services.getCharacters()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    emit(DataState.Result(body))
                } else {
                    emit(DataState.Error("Response body is null"))
                }
            } else {
                emit(DataState.Error("Failed to fetch characters: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(DataState.Error(e.message))
        }
    }
}
