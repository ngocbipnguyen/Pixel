package com.bachnn.feature.viewpager.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bachnn.feature.viewpager.R


@Composable
fun ContentScreen(modifier: Modifier = Modifier, navigateHome: (Int, Any) -> Unit) {
    ContentPage(modifier.fillMaxSize(), navigateHome)
}

@Composable
fun ContentPage(modifier: Modifier = Modifier, navigateHome: (Int, Any) -> Unit) {
    Column(
        modifier = modifier.padding(start = 4.dp, end = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(16.dp))
        Text(
            stringResource(R.string.title_content),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(16.dp))

        Text(
            stringResource(R.string.description_content),
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {navigateHome(10, "Get Inspired")},
            modifier = Modifier.width(200.dp),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.inversePrimary
            )
        ) {
            Text(stringResource(R.string.inspired_btn), style = MaterialTheme.typography.titleLarge)
        }

    }
}


@Preview(showBackground = true)
@Composable
fun ContentPreview() {
    ContentScreen(navigateHome =  { action, data ->

    })
}