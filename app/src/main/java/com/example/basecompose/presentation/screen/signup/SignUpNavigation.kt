package com.example.basecompose.presentation.screen.signup

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.basecompose.presentation.navigation.AuthRoute

fun NavController.navigateToSignUp(navOptions: NavOptions? = null) {
    navigate(AuthRoute.SignUp, navOptions)
}

fun NavGraphBuilder.signUpScreen(
    onBack: () -> Unit
) {
    composable<AuthRoute.SignUp> {
        SignUpScreen(onBack = onBack)
    }
}
