# Compose Nav

[[Maven Central](https://img.shields.io/maven-central/v/tech.kotlinlang/navigation.svg)](https://central.sonatype.com/artifact/tech.kotlinlang/navigation)
### Streamlined Navigation for Jetpack Compose
Compose Nav is a robust library designed to simplify and enhance navigation management within Jetpack Compose applications. By leveraging a clean and intuitive API, Compose Nav allows developers to organize and manage their app navigation effortlessly, ensuring a seamless user experience and maintainable codebase.

[========]

## How to use

1. Add the Compose Nav dependency to your project's `build.gradle` file.
   #### Using Groovy
    ```groovy
    dependencies {
      implementation("com.example:compose-nav:<version>")
    }
    ```
   #### Using kts
    ```kotlin
    dependencies {
      implementation "com.example:compose-nav:<version>"
    }
    ```

2. Use `UiNavigation` which helps to create a navigtation group or screen.
    ```kotlin
    UiNavigation(
      modifier = Modifier
        .fillMaxSize(),
      screenInfoGroup = ScreenInfoType.Group(
        startDestinationInfo = MainScreenInfo.SplashScreenInfo,
        screenInfoList = MainScreenInfo.all,
      )
    )
    ```

3. Create `MainScreenInfo.kt` and add all screen information using `ScreenInfo` class.
   ```kotlin
   object MainScreenInfo {
     val SplashScreenInfo = ScreenInfo(
       name = "splashScreen",
       screenInfoType = ScreenInfoType.Screen {
         SplashScreen()
       },
    )

    val DashBoardScreenInfo = ScreenInfo(
        name = "dashboardScreen",
        screenInfoType = ScreenInfoType.Screen {
            DashBoardScreen()
        },
    )

    val all get() = listOf(SplashScreenInfo, DashBoardScreenInfo)
   }
   ```

4. Now we need to navigate from one screen to another and which needs a nav controller and so we can access the same using `currentNavControllerOrThrow`.

   ```kotlin
    // currentNavControllerOrThrow can only be accessed inside composable function
    val navController = currentNavControllerOrThrow
    navController.pushNew(MainScreenInfo.DashBoardScreenInfo)
   ```

[========]

## Useful functions

#### List of Important nav contoller actions are listed below

| S. No. | Function name           | Parameters      | Description                                                                                                                                       |
|--------|-------------------------|-----------------|---------------------------------------------------------------------------------------------------------------------------------------------------|
| 1      | pushNew                 | ScreenInfo      | Push the given screenInfo to current screen and push the current screen into backstack                                                            |
| 2      | pushNewWithData         | ScreenInfo, map | Works same as `pushNew` but also helps to send extra information to next screen                                                                   |
| 3      | pushNewAndClear         | ScreenInfo      | Works same as `pushNew` with a difference that this remove your current screen and all previous screens from stack                                |
| 4      | pushNewWithDataAndClear | ScreenInfo, map | Works same as `pushNewWithData` with a difference that this remove your current screen and all previous screens from stack                        |
| 5      | popBack                 | --              | Remove the current screen from top of stack and navigate to previous screen if exists                                                             |
| 6      | popBack                 | --              | Remove the current screen from top of stack and navigate to previous screen if exists                                                             |
| 7      | popBackWithData         | key, value      | Works same as `popBack` but also helps to send some data back to previous screen                                                                  |
| 8      | accessDataAndClear      | key             | This function helps us to retrieve data which is shared from previous screen which is closed using `popBackWithData`. How to use is shown below * |

```kotlin
val uiController = currentNavControllerOrThrow
LaunchedEffet(Unit) {
	val isSucceded = uiController.accessDataAndClear<Boolean>("success")
}
```

[========]


