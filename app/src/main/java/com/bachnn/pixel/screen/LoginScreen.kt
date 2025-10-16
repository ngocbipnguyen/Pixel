package com.bachnn.pixel.screen

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bachnn.pixel.R
import com.bachnn.pixel.viewmodel.LoginViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onClick: () -> Unit, viewModel: LoginViewModel = hiltViewModel()) {
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
        LoginPage(modifier = Modifier.padding(top = paddingValues.calculateTopPadding()), viewModel)
    }
}

@Composable
fun LoginPage(modifier: Modifier, viewModel: LoginViewModel) {

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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, bottom = 24.dp, end = 24.dp)
                .align(alignment = Alignment.BottomCenter),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = {
                viewModel.loginByGoogle(activity)
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
        }
    }

}


@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(onClick = {
        
    })
}
