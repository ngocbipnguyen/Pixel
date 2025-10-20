package com.bachnn.pixel.viewmodel

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bachnn.data.model.User
import com.bachnn.pixel.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.security.MessageDigest
import java.util.UUID
import javax.inject.Inject


sealed interface LoginUiState {
    data class Success(val isLogin: Boolean) : LoginUiState
    object Loading : LoginUiState
}

@HiltViewModel
class LoginViewModel @Inject constructor(@ApplicationContext val context: Context) : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    var loginUiState: LoginUiState by mutableStateOf(LoginUiState.Loading)
        private set

    init {
        loginUiState = LoginUiState.Loading
        viewModelScope.launch {
            delay(3000)
            val isLogin = auth.currentUser != null
            loginUiState = LoginUiState.Success(isLogin)
        }
    }


    fun loginByGoogle(
        activity: Activity,
        signInSuccess: (User) -> Unit,
        signInFailure: (String) -> Unit
    ) {

        // Generate a nonce (a random number used once)
        val ranNonce: String = UUID.randomUUID().toString()
        val bytes: ByteArray = ranNonce.toByteArray()
        val md: MessageDigest = MessageDigest.getInstance("SHA-256")
        val digest: ByteArray = md.digest(bytes)
        val hashedNonce: String = digest.fold("") { str, it -> str + "%02x".format(it) }


        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(context.getString(R.string.default_web_client_id))
            .setNonce(hashedNonce)
            .build()
        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        val credentialManager = CredentialManager
            .create(activity)


        viewModelScope.launch {

            try {
                val result = credentialManager.getCredential(
                    context = activity,
                    request = request
                )
                val credential = result.credential

                val googleTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)

                val googleToken = googleTokenCredential.idToken

                val authCredential =
                    GoogleAuthProvider.getCredential(googleToken, null)

                val task = auth.signInWithCredential(authCredential).await()

                var user: User? = null

                task.user?.let { it ->
                    val uid = it.uid
                    val email = it.email.toString()
                    val name = it.displayName.toString()
                    val urlPhoto = it.photoUrl.toString()
                    user = User(uid, name, email, urlPhoto)
                    signInSuccess(user)

                }

                Log.e("okkk", "google token : $googleToken")
            } catch (e: Exception) {
                signInFailure(e.message.toString())
                Log.e("okkk", " e : ${e.toString()}")
            }
        }
    }
}