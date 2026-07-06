package com.example.basecompose.presentation.screen.detail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.basecompose.presentation.navigation.RootRoute

fun NavController.navigateToDetail(songId: Int, navOptions: NavOptions? = null) {
    navigate(RootRoute.Detail(songId), navOptions)
}

fun NavGraphBuilder.detailScreen(
    onBack: () -> Unit
) {
    composable<RootRoute.Detail> {
        DetailScreen(onBack = onBack)
    }
}
