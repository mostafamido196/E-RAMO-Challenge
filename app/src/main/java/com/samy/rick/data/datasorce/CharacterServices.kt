package com.samy.rick.data.datasorce

import com.samy.rick.pojo.CharacterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterServices {
    @GET("character")
    suspend fun getCharacters(): Response<CharacterResponse>
}
