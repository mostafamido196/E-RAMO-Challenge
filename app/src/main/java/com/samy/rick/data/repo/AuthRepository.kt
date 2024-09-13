package com.samy.rick.data.repo

import com.google.firebase.auth.FirebaseUser
import com.samy.rick.data.datasorce.AuthDataSource
import com.samy.rick.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepository(private val authDataSource: AuthDataSource) {
    suspend fun registerUser(
        name: String,
        email: String,
        password: String,
        age: Int,
    ): Flow<DataState<FirebaseUser?>> = flow {
        emit(DataState.Loading)
        try {
            val user = authDataSource.register(name, email, password, age)
            emit(DataState.Result(user))
        } catch (e: Exception) {
            emit(DataState.Error(e.message))
        }
    }

    fun login(email: String, password: String): Flow<DataState<FirebaseUser?>> = flow {
        emit(DataState.Loading)
        try {
            val user = authDataSource.login(email, password)
            emit(DataState.Result(user))
        } catch (e: Exception) {
            emit(DataState.Error(e.message))
        }
    }
}
