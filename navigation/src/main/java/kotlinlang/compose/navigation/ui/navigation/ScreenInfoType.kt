package kotlinlang.compose.navigation.ui.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import com.google.gson.Gson

sealed class ScreenInfoType {
    data class Group(
        val startDestinationInfo: ScreenInfo,
        val screenInfoList: List<ScreenInfo>,
        val startDestinationMap: Map<String, Any> = emptyMap()
    ) : ScreenInfoType() {
        val startDestination: String
            get() {
                val routeBuilder = StringBuilder()
                routeBuilder.append(startDestinationInfo.name)

                startDestinationMap.entries.forEachIndexed { index, (key, value) ->
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
                return routeBuilder.toString()
            }
    }

    data class Screen(val content: @Composable () -> Unit) : ScreenInfoType()
}