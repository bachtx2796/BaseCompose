package com.example.basecompose.presentation.screen.auth

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.example.basecompose.presentation.navigation.AuthRoute
import com.example.basecompose.presentation.navigation.AuthStart

/**
 * Lối vào DUY NHẤT của Auth journey. Dữ liệu ([songId], [start]) quyết định journey
 * mở ra ở màn nào — logic gói gọn tại đây, quyết định TRƯỚC khi điều hướng nên
 * không có màn trung gian / màn trắng.
 */
fun NavController.navigateToAuth(
    songId: Int,
    start: AuthStart = AuthStart.SignIn,
    navOptions: NavOptions? = null
) {
    val target: Any = when (start) {
        AuthStart.SignIn -> AuthRoute.SignIn(songId)
        AuthStart.SignUp -> AuthRoute.SignUp(songId)
    }
    navigate(target, navOptions)
}
