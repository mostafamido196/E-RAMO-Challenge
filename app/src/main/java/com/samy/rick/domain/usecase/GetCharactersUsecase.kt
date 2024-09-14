package com.samy.rick.domain.usecase

import androidx.paging.PagingData
import com.google.firebase.auth.FirebaseUser
import com.samy.rick.data.repository.AuthRepository
import com.samy.rick.data.repository.CharacterRepository
import com.samy.rick.pojo.CharacterResponse
import com.samy.rick.pojo.CharacterResult
import com.samy.rick.utils.DataState
import kotlinx.coroutines.flow.Flow
import okhttp3.Response
import javax.inject.Inject

class GetCharactersUsecase @Inject constructor(
    private val repository: CharacterRepository,
) {
    
    operator fun invoke(): Flow<DataState<CharacterResponse>> {
        return repository.getCharacterPagingData()
    }

}
