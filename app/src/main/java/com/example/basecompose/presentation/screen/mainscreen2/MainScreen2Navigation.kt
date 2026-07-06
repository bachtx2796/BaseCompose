package com.example.basecompose.presentation.screen.mainscreen2

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.basecompose.presentation.navigation.MainTabRoute

fun NavController.navigateToMainScreen2(navOptions: NavOptions? = null) {
    navigate(MainTabRoute.MainScreen2, navOptions)
}

fun NavGraphBuilder.mainScreen2() {
    composable<MainTabRoute.MainScreen2> {
        MainScreen2Screen()
    }
}
