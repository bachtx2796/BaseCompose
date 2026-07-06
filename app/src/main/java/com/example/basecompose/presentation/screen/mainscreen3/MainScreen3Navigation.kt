package com.example.basecompose.presentation.screen.mainscreen3

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.basecompose.presentation.navigation.MainTabRoute

fun NavController.navigateToMainScreen3(navOptions: NavOptions? = null) {
    navigate(MainTabRoute.MainScreen3, navOptions)
}

fun NavGraphBuilder.mainScreen3() {
    composable<MainTabRoute.MainScreen3> {
        MainScreen3Screen()
    }
}
