package com.samy.rick.domain.usecase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.samy.rick.data.repo.AuthRepository
import com.samy.rick.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val repository: AuthRepository,
) {

    suspend operator fun invoke(email: String, password: String): Flow<DataState<FirebaseUser?>> {
        return repository.login(email, password)
    }
}
