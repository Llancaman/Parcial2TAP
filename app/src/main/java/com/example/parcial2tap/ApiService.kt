package com.example.parcial2tap

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET("fruit/all")
    suspend fun getFruits(): Response<List<Fruits>>

    @GET
    suspend fun getFruitsDetalle(@Url url : String): Response<Fruits>
}