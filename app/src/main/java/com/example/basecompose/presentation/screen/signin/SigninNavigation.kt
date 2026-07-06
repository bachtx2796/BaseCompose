package com.example.basecompose.presentation.screen.signin

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.basecompose.ext.sharedGraphViewModel
import com.example.basecompose.presentation.navigation.AuthRoute

fun NavController.navigateToAuth(navOptions: NavOptions? = null) {
    navigate(AuthRoute, navOptions)
}

fun NavGraphBuilder.signInScreen(
    navController: NavController,
    onNavigateToSignUp: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    composable<AuthRoute.SignIn> { backStackEntry ->
        val shareVM: SigninViewModel = backStackEntry.sharedGraphViewModel(
            navController = navController,
            graphRoute = AuthRoute
        )

        SignInScreen(
            shareVM,
            onNavigateToSignUp = onNavigateToSignUp,
            onLoginSuccess = onLoginSuccess
        )
    }
}
