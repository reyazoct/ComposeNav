package tech.kotlinlang.compose.nav.ui.screens

import kotlinlang.compose.navigation.ui.navigation.ScreenInfo
import kotlinlang.compose.navigation.ui.navigation.ScreenInfoType
import tech.kotlinlang.compose.nav.ui.screens.details.DetailsScreen
import tech.kotlinlang.compose.nav.ui.screens.home.HomeScreen

object MainScreenInfo {
    val HomeScreenInfo = ScreenInfo(
        name = "homeScreen",
        screenInfoType = ScreenInfoType.Screen {
            HomeScreen()
        }
    )

    val DetailsScreenInfo = ScreenInfo(
        name = "detailsScreen",
        screenInfoType = ScreenInfoType.Screen {
            DetailsScreen()
        }
    )

    val all = listOf(HomeScreenInfo, DetailsScreenInfo)
}