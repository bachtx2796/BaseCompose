package com.example.basecompose.presentation.navigation

import kotlinx.serialization.Serializable

object RootRoute {
    @Serializable
    object Main

    @Serializable
    data class Detail(val songId: Int)

    @Serializable
    object Hdsd
}

@Serializable
object AuthRoute {
    @Serializable
    object SignIn

    @Serializable
    object SignUp
}

object MainTabRoute {
    @Serializable
    object MainScreen1

    @Serializable
    object MainScreen2

    @Serializable
    object MainScreen3
}
