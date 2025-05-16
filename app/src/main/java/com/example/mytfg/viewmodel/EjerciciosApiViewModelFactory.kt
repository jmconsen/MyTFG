package com.example.mytfg.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mytfg.network.RetrofitClient

class EjerciciosApiViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EjerciciosApiViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EjerciciosApiViewModel(RetrofitClient.apiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
