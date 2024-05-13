package tech.kotlinlang.compose.nav

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import kotlinlang.compose.navigation.ui.navigation.ScreenInfoType
import kotlinlang.compose.navigation.ui.navigation.UiNavigation
import tech.kotlinlang.compose.nav.ui.screens.MainScreenInfo
import tech.kotlinlang.compose.nav.ui.theme.ComposeNavTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeNavTheme {
                Surface(color = MaterialTheme.colorScheme.surface) {
                    UiNavigation(
                        modifier = Modifier.fillMaxSize(),
                        screenInfoGroup = ScreenInfoType.Group(
                            startDestinationInfo = MainScreenInfo.HomeScreenInfo,
                            screenInfoList = MainScreenInfo.all,
                        )
                    )
                }
            }
        }
    }
}

