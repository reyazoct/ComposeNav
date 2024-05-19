## Compose Nav

![Maven Central Version](https://img.shields.io/maven-central/v/tech.kotlinlang/navigation?style=for-the-badge&logo=jetpackcompose&logoColor=FFFFFF)

### Streamlined Navigation for Jetpack Compose

Compose Nav is a robust library designed to simplify and enhance navigation management within Jetpack Compose applications. By leveraging a clean and intuitive API, Compose Nav allows developers to organise and manage their app navigation effortlessly, ensuring a seamless user experience and maintainable codebase.

## How to use

1.  Add the Compose Nav dependency to your project's `build.gradle` file.

    #### Using Groovy

    ```plaintext
    dependencies {
      implementation "tech.kotlinlang:navigation:<version>"
    }
    ```

    #### Using kts

    ```plaintext
    dependencies {
      implementation("tech.kotlinlang:navigation:<version>")
    }
    ```

2.  Use `UiNavigation` which helps to create a navigtation group or screen.

    ```plaintext
    UiNavigation(
      modifier = Modifier
        .fillMaxSize(),
      screenInfoGroup = ScreenInfoType.Group(
        startDestinationInfo = MainScreenInfo.SplashScreenInfo,
        screenInfoList = MainScreenInfo.all,
      )
    )
    ```

3.  Create `MainScreenInfo.kt` and add all screen information using `ScreenInfo` class.

    ```plaintext
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

4.  Now we need to navigate from one screen to another and which needs a nav controller and so we can access the same using `currentNavControllerOrThrow`.

    ```plaintext
     // currentNavControllerOrThrow can only be accessed inside composable function
     val navController = currentNavControllerOrThrow
     navController.pushNew(MainScreenInfo.DashBoardScreenInfo)
    ```


## Useful functions

#### List of Important nav contoller actions are listed below

| S. No. | Function name           | Parameters      | Description                                                                                                                                        |
|--------|-------------------------|-----------------|----------------------------------------------------------------------------------------------------------------------------------------------------|
| 1      | pushNew                 | ScreenInfo      | Push the given screenInfo to current screen and push the current screen into backstack                                                             |
| 2      | pushNewWithData         | ScreenInfo, map | Works same as `pushNew` but also helps to send extra information to next screen                                                                    |
| 3      | pushNewAndClear         | ScreenInfo      | Works same as `pushNew` with a difference that this remove your current screen and all previous screens from stack                                 |
| 4      | pushNewWithDataAndClear | ScreenInfo, map | Works same as `pushNewWithData` with a difference that this remove your current screen and all previous screens from stack                         |
| 5      | popBack                 | \--             | Remove the current screen from top of stack and navigate to previous screen if exists                                                              |
| 6      | popBack                 | \--             | Remove the current screen from top of stack and navigate to previous screen if exists                                                              |
| 7      | popBackWithData         | key, value      | Works same as `popBack` but also helps to send some data back to previous screen                                                                   |
| 8      | accessDataAndClear      | key             | This function helps us to retrieve data which is shared from previous screen which is closed using `popBackWithData`. How to use is shown below \* |

```plaintext
val uiController = currentNavControllerOrThrow
LaunchedEffet(Unit) {
    val isSucceded = uiController.accessDataAndClear<Boolean>("success")
}
```

## About Developer

>  #### Reyaz Ahmad
> [![GitHub followers](https://img.shields.io/github/followers/reyazoct?style=for-the-badge&logo=github&label=Github)](https://github.com/reyazoct) [![Website](https://img.shields.io/website?url=https%3A%2F%2Freyaz.live&style=for-the-badge&label=reyaz.live)](https://reyaz.live) [![Website](https://img.shields.io/website?url=https%3A%2F%2Fwww.linkedin.com&style=for-the-badge&logo=linkedin&label=linkedin)](https://www.linkedin.com/in/ahmad-reyaz) [![Website](https://img.shields.io/website?url=https%3A%2F%2Fleetcode.com&style=for-the-badge&logo=leetcode&label=Leetcode)](https://leetcode.com/u/reyazoct)