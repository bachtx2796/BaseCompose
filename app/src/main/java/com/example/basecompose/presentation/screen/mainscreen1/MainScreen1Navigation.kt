package com.example.basecompose.presentation.screen.mainscreen1

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.basecompose.presentation.navigation.MainTabRoute

fun NavController.navigateToMainScreen1(navOptions: NavOptions? = null) {
    navigate(MainTabRoute.MainScreen1, navOptions)
}

fun NavGraphBuilder.mainScreen1(
    navigateToDetail: (Int) -> Unit,
    navigateToSignIn: () -> Unit,
    navigateToHdsd: () -> Unit
) {
    composable<MainTabRoute.MainScreen1> {
        MainScreen1Screen(
            navigateToDetail = navigateToDetail,
            navigateToSignIn = navigateToSignIn,
            navigateToHdsd = navigateToHdsd
        )
    }
}
