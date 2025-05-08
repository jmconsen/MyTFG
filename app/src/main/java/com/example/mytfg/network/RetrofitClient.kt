package com.example.mytfg.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val apiService: ExerciseApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://exercisedb.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExerciseApiService::class.java)
    }
}
