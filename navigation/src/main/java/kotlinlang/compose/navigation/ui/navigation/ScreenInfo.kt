package kotlinlang.compose.navigation.ui.navigation

data class ScreenInfo(
    val name: String,
    val screenInfoType: ScreenInfoType,
    val paramNames: List<String> = emptyList(),
    val extraData: Map<String, Any> = emptyMap()
) {
    val destination: String
        get() {
            val routeNameBuilder = StringBuilder()
            routeNameBuilder.append(name)
            paramNames.forEachIndexed { index, item ->
                val appender = if (index == 0) "?" else "&"
                routeNameBuilder.append("$appender$item={$item}")
            }
            return routeNameBuilder.toString()
        }
}