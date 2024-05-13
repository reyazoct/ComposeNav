package tech.kotlinlang.compose.nav.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinlang.compose.navigation.ui.navigation.currentNavControllerOrThrow
import tech.kotlinlang.compose.nav.ui.screens.MainScreenInfo

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "This is home screen",
        )
        Spacer(modifier = Modifier.height(16.dp))
        val currentNavController = currentNavControllerOrThrow
        Button(
            onClick = {
                currentNavController.pushNew(MainScreenInfo.DetailsScreenInfo)
            },
        ) {
            Text(text = "Go to details")
        }
    }
}