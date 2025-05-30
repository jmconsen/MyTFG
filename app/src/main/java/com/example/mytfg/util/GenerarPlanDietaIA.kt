package com.example.mytfg.util

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

private const val TAG_DIETA = "GroqDieta"

suspend fun generarPlanDietaIA(
    edad: String,
    peso: String,
    altura: String,
    frecuencia: String,
    objetivo: String
): String? = withContext(Dispatchers.IO) {
    try {
        val groqApiKey = "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" // Token de Groq API

        val prompt = "Genera una dieta semanal personalizada para una persona de $edad años, $peso kg, $altura cm de altura, que entrena $frecuencia días a la semana y cuyo objetivo es $objetivo. La dieta debe estar en español, incluir desayuno, comida, cena y snacks, y ser equilibrada."

        val json = JSONObject()
        json.put("model", "meta-llama/llama-4-scout-17b-16e-instruct")
        json.put("temperature", 0.7)

        val messagesArray = org.json.JSONArray()
        val userMessage = JSONObject()
        userMessage.put("role", "user")
        userMessage.put("content", prompt)
        messagesArray.put(userMessage)

        json.put("messages", messagesArray)

        val body = json.toString().toRequestBody("application/json".toMediaType())
        val request = Request.Builder()
            .url("https://api.groq.com/openai/v1/chat/completions")
            .addHeader("Authorization", "Bearer $groqApiKey")
            .addHeader("Content-Type", "application/json")
            .post(body)
            .build()

        val client = OkHttpClient()
        val response = client.newCall(request).execute()
        val responseBody = response.body?.string()

        Log.d(TAG_DIETA, "Código de respuesta: ${response.code}")
        Log.d(TAG_DIETA, "Cuerpo de respuesta: $responseBody")

        if (response.isSuccessful && responseBody != null) {
            val jsonResponse = JSONObject(responseBody)
            val content = jsonResponse
                .getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content")
            content
        } else {
            Log.e(TAG_DIETA, "Error en la respuesta: ${response.code} - ${response.message}")
            null
        }
    } catch (e: Exception) {
        Log.e(TAG_DIETA, "Excepción al llamar a Groq: ${e.message}", e)
        null
    }
}
