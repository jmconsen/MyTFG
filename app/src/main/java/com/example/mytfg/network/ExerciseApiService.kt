package com.example.mytfg.network
import com.example.mytfg.model.EjercicioApi
import retrofit2.http.GET
import retrofit2.http.Path

interface ExerciseApiService {
    @GET("exercises/bodyPart/{bodyPart}")
    suspend fun getExercisesByBodyPart(
        @Path("bodyPart") bodyPart: String
    ): List<EjercicioApi>
}

