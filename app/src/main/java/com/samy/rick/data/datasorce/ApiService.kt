package com.samy.rick.data.datasorce

import com.samy.rick.pojo.model.DataModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {

    @GET("home")
    suspend fun getData(
        @Header("Platform") platform: String,
        ): Response<DataModel>

}