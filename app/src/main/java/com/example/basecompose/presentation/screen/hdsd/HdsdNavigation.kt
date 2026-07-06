package com.example.basecompose.presentation.screen.hdsd

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.basecompose.presentation.navigation.RootRoute

fun NavController.navigateToHdsd(navOptions: NavOptions? = null) {
    navigate(RootRoute.Hdsd, navOptions)
}

fun NavGraphBuilder.hdsdScreen(
    onBack: () -> Unit
) {
    composable<RootRoute.Hdsd> {
        HdsdScreen(onBack = onBack)
    }
}
