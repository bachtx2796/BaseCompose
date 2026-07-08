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

/** Journey mở ra ở màn nào — dữ liệu quyết định start, do [navigateToAuth] xử lý. */
enum class AuthStart { SignIn, SignUp }

@Serializable
object AuthRoute {
    @Serializable
    data class SignIn(val songId: Int)

    @Serializable
    data class SignUp(val songId: Int)
}

object MainTabRoute {
    @Serializable
    object MainScreen1

    @Serializable
    object MainScreen2

    @Serializable
    object MainScreen3
}
