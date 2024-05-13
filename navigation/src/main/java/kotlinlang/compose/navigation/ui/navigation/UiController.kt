package kotlinlang.compose.navigation.ui.navigation

import android.net.Uri
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.google.gson.Gson

class UiController(
    val navController: NavHostController,
    private val screenInfoGroup: ScreenInfoType.Group,
) {
    private val _currentScreenInfoState = MutableStateFlow(screenInfoGroup.startDestinationInfo)
    val currentScreenInfoState = _currentScreenInfoState.asStateFlow()

    @OptIn(DelicateCoroutinesApi::class)
    fun pushNew(screenInfo: ScreenInfo) {
        navController.navigate(screenInfo.name)
        GlobalScope.launch {
            when (val screenInfoType = screenInfo.screenInfoType) {
                is ScreenInfoType.Group -> _currentScreenInfoState.emit(screenInfoType.startDestinationInfo)
                is ScreenInfoType.Screen -> _currentScreenInfoState.emit(screenInfo)
            }
        }
    }

    fun pushNewWithData(screenInfo: ScreenInfo, map: Map<String, Any>) {
        pushNewWithDataAndClearInternal(screenInfo, map, false)
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun pushNewWithDataAndClearInternal(screenInfo: ScreenInfo, map: Map<String, Any>, clear: Boolean) {
        val routeBuilder = StringBuilder()
        routeBuilder.append(screenInfo.name)

        map.entries.forEachIndexed { index, (key, value) ->
            val appender = if (index == 0) "?" else "&"
            val rawValue = when (value) {
                is String -> value
                is Int -> value.toString()
                is Double -> value.toString()
                is Float -> value.toString()
                else -> Uri.encode(Gson().toJson(value))
            }
            routeBuilder.append("$appender$key=$rawValue")
        }

        if (clear) navController.navigate(routeBuilder.toString()) { popUpTo(navController.graph.id) { inclusive = true } }
        else navController.navigate(routeBuilder.toString())
        GlobalScope.launch {
            when (val screenInfoType = screenInfo.screenInfoType) {
                is ScreenInfoType.Group -> _currentScreenInfoState.emit(screenInfoType.startDestinationInfo)
                is ScreenInfoType.Screen -> _currentScreenInfoState.emit(screenInfo)
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun pushNewAndClear(screenInfo: ScreenInfo) {
        navController.navigate(screenInfo.name) { popUpTo(navController.graph.id) { inclusive = true } }
        GlobalScope.launch {
            _currentScreenInfoState.emit(screenInfo)
        }
    }

    fun pushNewWithDataAndClear(screenInfo: ScreenInfo, map: Map<String, Any>) {
        pushNewWithDataAndClearInternal(screenInfo, map, true)
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun popBack() {
        if (navController.popBackStack()) {
            GlobalScope.launch {
                findScreenInfo(
                    screenInfoGroup,
                    navController.currentBackStackEntry?.destination,
                )?.let {
                    _currentScreenInfoState.emit(it)
                }
            }
        }
    }

    private fun findScreenInfo(group: ScreenInfoType.Group, navDestination: NavDestination?): ScreenInfo? {
        group.screenInfoList.forEach { screenInfo ->
            val foundedScreenInfo = when (val screenInfoType = screenInfo.screenInfoType) {
                is ScreenInfoType.Group -> {
                    if (screenInfo.destination == navDestination?.parent?.route) findScreenInfo(screenInfoType, navDestination)
                    else null
                }

                is ScreenInfoType.Screen -> {
                    if (screenInfo.name == navDestination?.route) screenInfo
                    else null
                }
            }
            if (foundedScreenInfo != null) return foundedScreenInfo
        }
        return null
    }

    fun <T> accessDataAndClear(key: String): T? {
        val data = navController.currentBackStackEntry?.savedStateHandle?.get<T>(key)
        navController.currentBackStackEntry?.savedStateHandle?.remove<T>(key)
        return data
    }

    fun <T> popBackWithData(key: String, value: T) {
        navController.previousBackStackEntry?.savedStateHandle?.set(key, value)
        popBack()
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun <T> popBackToScreenWithData(screenInfo: ScreenInfo, key: String, value: T) {
        if (navController.popBackStack(route = screenInfo.name, inclusive = false)) {
            navController.currentBackStackEntry?.savedStateHandle?.set(key, value)
            GlobalScope.launch {
                findScreenInfo(
                    screenInfoGroup,
                    navController.currentBackStackEntry?.destination,
                )?.let {
                    _currentScreenInfoState.emit(it)
                }
            }
        }
    }
}