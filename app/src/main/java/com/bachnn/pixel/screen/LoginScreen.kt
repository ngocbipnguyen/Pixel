package com.bachnn.pixel.screen

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.bachnn.data.model.User
import com.bachnn.pixel.R
import com.bachnn.pixel.viewmodel.LoginViewModel
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.firebase.Firebase
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    signInSuccess: (User) -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.login_title),
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        )
    }) { paddingValues ->
        LoginPage(
            modifier = Modifier.padding(top = paddingValues.calculateTopPadding()),
            viewModel,
            signInSuccess,
            signInFailure = { log ->

            }
        )
    }
}

@Composable
fun LoginPage(
    modifier: Modifier,
    viewModel: LoginViewModel,
    signInSuccess: (User) -> Unit,
    signInFailure: (String) -> Unit
) {

    val context = LocalContext.current
    val activity = context as? Activity ?: return

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.icons_message),
            contentDescription = stringResource(id = R.string.splash_description),
            modifier = Modifier
                .width(120.dp)
                .height(120.dp)
                .align(alignment = Alignment.Center)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 50.dp, bottom = 30.dp, end = 50.dp)
                .align(alignment = Alignment.BottomCenter),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                onClick = {
                    viewModel.loginByGoogle(activity, signInSuccess, signInFailure)
                }) {
                Image(
                    painter = painterResource(id = R.drawable.iconsgoogle),
                    contentDescription = ""
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    stringResource(id = R.string.login_by_google),
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(Modifier.height(12.dp))

            SigninFacebookButton(
                onSignInFailed = {

                },
                signInSuccess
            )
        }
    }

}

@Composable
fun SigninFacebookButton(
    onSignInFailed: (Exception) -> Unit,
    signInSuccess: (User) -> Unit,
) {
    val scope = rememberCoroutineScope()
    AndroidView(
        { context ->
            LoginButton(context).apply {
                text = context.getString(R.string.login_by_facebook)
                textSize = 20.0f
                val callbackManager = CallbackManager.Factory.create()
                setPermissions("email", "public_profile")
                registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                    override fun onCancel() {
                        // do nothing
                    }

                    override fun onError(error: FacebookException) {
                        onSignInFailed(error)
                    }

                    override fun onSuccess(result: LoginResult) {
                        scope.launch {
                            val token = result.accessToken.token
                            val credential = FacebookAuthProvider.getCredential(token)
                            val authResult = Firebase.auth.signInWithCredential(credential).await()
                            if (authResult.user != null) {
                                signInSuccess(
                                    User(
                                        uid = authResult.user!!.uid ?: "",
                                        email = authResult.user!!.email ?: "",
                                        name = authResult.user!!.displayName ?: "",
                                        photoUrl = (authResult.user!!.photoUrl ?: "").toString()
                                    )
                                )
                            } else {
                                onSignInFailed(RuntimeException("Could not sign in with Firebase"))
                            }
                        }
                    }
                })
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}


@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(signInSuccess = {

    })
}
