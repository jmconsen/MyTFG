package com.example.mytfg.repository

import com.example.mytfg.model.Ejercicio
import com.google.firebase.firestore.FirebaseFirestore

class EjerciciosRepository {
    private val db = FirebaseFirestore.getInstance()

    fun getEjercicios(callback: (List<Ejercicio>) -> Unit) {
        db.collection("ejercicios").get()
            .addOnSuccessListener { result ->
                val ejercicios = result.map { doc ->
                    doc.toObject(Ejercicio::class.java)
                }
                callback(ejercicios)
            }
            .addOnFailureListener {
                callback(emptyList())
            }
    }
}
