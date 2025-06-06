package com.example.mytfg.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val request = chain.request().newBuilder()
                    .addHeader("X-RapidAPI-Key", "fcbc264352msh99e1ab458df058fp18c8a6jsn2d02734d5cf8")
                    .addHeader("X-RapidAPI-Host", "exercisedb.p.rapidapi.com")
                    .build()
                return chain.proceed(request)
            }
        })
        .build()

    val apiService: ExerciseApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://exercisedb.p.rapidapi.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExerciseApiService::class.java)
    }
}
