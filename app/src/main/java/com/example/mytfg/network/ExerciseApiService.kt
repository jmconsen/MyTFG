package com.example.mytfg.network

import com.example.mytfg.model.EjercicioApi
import retrofit2.http.GET
import retrofit2.http.Headers

interface ExerciseApiService {
    @Headers(
        "X-RapidAPI-Key: fcbc264352msh99e1ab458df058fp18c8a6jsn2d02734d5cf8", // Pega aquí tu clave personal
        "X-RapidAPI-Host: exercisedb.p.rapidapi.com"
    )
    @GET("exercises")
    suspend fun getExercises(): List<EjercicioApi>
}
