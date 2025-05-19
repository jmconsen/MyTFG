package com.example.mytfg.util

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

private const val TAG = "HuggingFace"

suspend fun generarPlanEntrenamientoIA(
    edad: String, peso: String, altura: String, frecuencia: String, objetivo: String
): String? = withContext(Dispatchers.IO) {
    try {
        // Puedes obtener un token gratuito en https://huggingface.co/settings/tokens
        val huggingFaceToken = "" // O déjalo vacío para modelos públicos
        val prompt = "Genera un plan de entrenamiento personalizado para una persona de $edad años, $peso kg, $altura cm, que entrena $frecuencia días a la semana y cuyo objetivo es $objetivo. El plan debe ser claro, estructurado y en español."

        val json = JSONObject()
        json.put("inputs", prompt)

        val body = json.toString().toRequestBody("application/json".toMediaType())
        val requestBuilder = Request.Builder()
            // Puedes cambiar el modelo por otro de HuggingFace compatible con texto (ej: mistralai/Mistral-7B-Instruct-v0.2)
            .url("https://api-inference.huggingface.co/models/meta-llama/Llama-2-7b-chat-hf")
            .post(body)

        if (huggingFaceToken.isNotEmpty()) {
            requestBuilder.addHeader("Authorization", "Bearer $huggingFaceToken")
        }

        val client = OkHttpClient()
        val request = requestBuilder.build()
        val response = client.newCall(request).execute()
        if (response.isSuccessful) {
            val responseBody = response.body?.string()
            if (responseBody != null) {
                Log.d(TAG, "Respuesta recibida: $responseBody")
                // La respuesta es un JSON array con un campo "generated_text"
                val respuestaJsonArray = org.json.JSONArray(responseBody)
                val generatedText = respuestaJsonArray.getJSONObject(0).getString("generated_text")
                generatedText
            } else {
                Log.e(TAG, "El body de la respuesta es null")
                null
            }
        } else {
            Log.e(TAG, "Error en la respuesta: ${response.code} - ${response.message}")
            null
        }
    } catch (e: Exception) {
        Log.e(TAG, "Excepción al llamar a HuggingFace: ${e.message}", e)
        null
    }
}
