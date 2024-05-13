package kotlinlang.compose.navigation.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import kotlinlang.compose.navigation.ui.navigation.ScreenInfoType.*
import java.net.URLDecoder

@Composable
fun UiNavigation(
    modifier: Modifier = Modifier,
    screenInfoGroup: Group,
    uiController: UiController = rememberUiController(screenInfoGroup = screenInfoGroup),
) {
    CompositionLocalProvider(
        LocalUiController provides uiController
    ) {
        NavHost(
            modifier = modifier,
            navController = uiController.navController,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(250),
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(250),
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(250),
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(250),
                )
            },
            startDestination = screenInfoGroup.startDestination,
        ) {
            buildNavGraph(screenInfoGroup.screenInfoList)
        }
    }
}

private fun NavGraphBuilder.buildNavGraph(screensList: List<ScreenInfo>) {
    screensList.forEach { screenInfo ->
        when (val screenInfoType = screenInfo.screenInfoType) {
            is Group -> if (screenInfoType.screenInfoList.isNotEmpty()) {
                navigation(
                    route = screenInfo.destination,
                    startDestination = screenInfoType.startDestination,
                    arguments = screenInfo.paramNames.map {
                        navArgument(it) { type = NavType.StringType }
                    }
                ) {
                    buildNavGraph(screensList = screenInfoType.screenInfoList)
                }
            }

            is Screen -> {
                composable(
                    route = screenInfo.destination,
                    arguments = screenInfo.paramNames.map {
                        navArgument(it) { type = NavType.StringType }
                    }
                ) {
                    screenInfoType.content()
                }
            }
        }
    }
}

@Composable
fun rememberUiController(screenInfoGroup: Group): UiController {
    val navController = rememberNavController()
    return remember {
        UiController(navController, screenInfoGroup)
    }
}

private val LocalUiController = compositionLocalOf<UiController?> { null }

@get:ReadOnlyComposable
val currentNavControllerOrThrow: UiController
    @Composable
    get() = LocalUiController.current ?: throw IllegalStateException("UiController not found")

fun <T> SavedStateHandle.getParamData(name: String, className: Class<T>): T? {
    val data = get<String>(name) ?: return null
    return if (className == String::class.java) data as? T
    else {
        val jsonString = data.replace("%(?![0-9a-fA-F]{2})".toRegex(), "%25").replace("\\+".toRegex(), "%2B")
        Gson().fromJson(URLDecoder.decode(jsonString, Charsets.UTF_8.name()), className)
    }
}