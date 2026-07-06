package com.example.basecompose.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface AppRoute
sealed interface AppGraph : AppRoute

object RootRoute {
    @Serializable
    object Main : AppRoute

    @Serializable
    data class Detail(val songId: Int) : AppRoute
}

object AuthRoute {
    @Serializable
    object Graph : AppGraph

    @Serializable
    object SignIn : AppRoute

    @Serializable
    object SignUp : AppRoute
}

object MainTabRoute {
    @Serializable
    object MainScreen1 : AppRoute

    @Serializable
    object MainScreen2 : AppRoute

    @Serializable
    object MainScreen3 : AppRoute
}
