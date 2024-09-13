package com.samy.rick.domain.usecase

import com.google.firebase.auth.FirebaseUser
import com.samy.rick.data.repo.AuthRepository
import com.samy.rick.utils.DataState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        name: String,
        email: String,
        password: String,
        age: Int,
    ): Flow<DataState<FirebaseUser?>> {
        return repository.registerUser(name,email, password,age)
    }
}

