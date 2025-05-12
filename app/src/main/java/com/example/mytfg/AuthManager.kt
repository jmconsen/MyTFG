package com.example.mytfg

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import com.example.mytfg.model.UsuarioAuth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.google.firebase.auth.FirebaseUser

class AuthManager(
    private val activity: Activity,
    clientId: String,
    private val googleSignInLauncher: ActivityResultLauncher<Intent>
) {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val googleSignInClient: GoogleSignInClient

    // Nuevo: Estado observable del usuario
    private val _currentUser = MutableStateFlow<UsuarioAuth?>(null)
    val currentUser: StateFlow<UsuarioAuth?> = _currentUser.asStateFlow()

    init {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(clientId)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(activity, googleSignInOptions)
        // Actualiza el usuario al iniciar
        updateCurrentUser(auth.currentUser)
    }

    private fun updateCurrentUser(firebaseUser: FirebaseUser?) {
        if (firebaseUser != null) {
            _currentUser.value = UsuarioAuth(
                name = firebaseUser.displayName ?: "Sin nombre",
                avatarUrl = firebaseUser.photoUrl?.toString() ?: "https://ui-avatars.com/api/?name=User"
            )
        } else {
            _currentUser.value = null
        }
    }

    fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent)
        Log.d("AuthManager", "Iniciando flujo de Google Sign-In.")
    }

    fun handleGoogleSignInResult(
        data: Intent?,
        onSuccess: (String) -> Unit,
        onFailure: (Exception?) -> Unit
    ) {
        Log.d("AuthManager", "Procesando resultado de Google Sign-In.")
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)
            Log.d("AuthManager", "ID Token obtenido: ${account.idToken}")

            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            auth.signInWithCredential(credential)
                .addOnCompleteListener(activity) { authTask ->
                    if (authTask.isSuccessful) {
                        val user = auth.currentUser
                        updateCurrentUser(user) // <--- Actualiza el usuario observable
                        Log.d("AuthManager", "Usuario autenticado: ${user?.email}")
                        onSuccess("Autenticación con Google exitosa.")
                    } else {
                        Log.e("AuthManager", "Error en la autenticación con Firebase.", authTask.exception)
                        onFailure(authTask.exception)
                    }
                }
        } catch (e: ApiException) {
            Log.e("AuthManager", "Error al obtener la cuenta de Google: ${e.localizedMessage}")
            onFailure(e)
        }
    }

    fun isUserLoggedIn(): Boolean {
        val isLoggedIn = auth.currentUser != null
        Log.d("AuthManager", "Estado de inicio de sesión: ${if (isLoggedIn) "Autenticado" else "No autenticado"}")
        return isLoggedIn
    }

    fun logout(
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        try {
            googleSignInClient.signOut().addOnCompleteListener {
                FirebaseAuth.getInstance().signOut()
                updateCurrentUser(null) // <--- Limpia el usuario observable
                onSuccess()
            }
        } catch (e: Exception) {
            onFailure(e)
        }
    }
}
