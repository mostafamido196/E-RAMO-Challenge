package com.samy.e_ramo.data.datasorce

import com.samy.e_ramo.pojo.model.DataModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {

    @GET("home")
    suspend fun getData(
        @Header("Platform") platform: String,
        ): Response<DataModel>

}