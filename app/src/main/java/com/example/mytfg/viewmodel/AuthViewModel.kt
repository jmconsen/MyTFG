package com.example.mytfg.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mytfg.model.UsuarioAuth
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class AuthUiState(
    val nombre: String = "",
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val errorMessage: String? = null,
    val userEmail: String? = null,
    val usuarioAuth: UsuarioAuth = UsuarioAuth(
        name = "Invitado",
        avatarUrl = "https://ui-avatars.com/api/?name=Invitado"
    )
)

class AuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState

    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun login() {
        val email = _uiState.value.email
        val password = _uiState.value.password
        if (email.isBlank() || password.isBlank()) {
            _uiState.value = _uiState.value.copy(errorMessage = "Por favor, introduzca email y contraseña.")
            return
        }

        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val displayName = user?.displayName ?: "Invitado"
                    val photoUrl = user?.photoUrl?.toString()
                        ?: "https://ui-avatars.com/api/?name=${displayName.replace(" ", "+")}"

                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isLoggedIn = true,
                        userEmail = user?.email,
                        usuarioAuth = UsuarioAuth(name = displayName, avatarUrl = photoUrl),
                        errorMessage = null
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isLoggedIn = false,
                        errorMessage = task.exception?.message ?: "Error de inicio de sesión"
                    )
                }
            }
    }

    fun logout() {
        auth.signOut()
        _uiState.value = AuthUiState()
    }
}




/*
package com.example.mytfg.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class AuthUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val errorMessage: String? = null,
    val userEmail: String? = null
)

class AuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState

    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun login() {
        val email = _uiState.value.email
        val password = _uiState.value.password
        if (email.isBlank() || password.isBlank()) {
            _uiState.value = _uiState.value.copy(errorMessage = "Por favor, ingrese email y contraseña.")
            return
        }
        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isLoggedIn = true,
                        userEmail = user?.email,
                        errorMessage = null
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isLoggedIn = false,
                        errorMessage = task.exception?.message ?: "Error de inicio de sesión"
                    )
                }
            }
    }

    fun logout() {
        auth.signOut()
        _uiState.value = AuthUiState()
    }
}


 */