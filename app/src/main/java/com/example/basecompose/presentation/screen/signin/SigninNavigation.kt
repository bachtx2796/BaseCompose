package com.example.basecompose.presentation.screen.signin

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.basecompose.ext.sharedGraphViewModel
import com.example.basecompose.presentation.navigation.AuthRoute

fun NavGraphBuilder.signInScreen(
    navController: NavController,
    onLoginSuccess: () -> Unit
) {
    composable<AuthRoute.SignIn> { backStackEntry ->
        val args = backStackEntry.toRoute<AuthRoute.SignIn>()
        val shareVM: SigninViewModel = backStackEntry.sharedGraphViewModel<SigninViewModel, AuthRoute>(
            navController = navController
        )

        // đọc arg của route rồi push vào shared VM (dùng chung cho cả graph)
        LaunchedEffect(args.songId) {
            shareVM.onAction(SigninContract.ViewEvent.SetSong(args.songId))
        }

        SignInScreen(
            shareVM,
            // đi tiếp trong journey — giữ nguyên songId của phiên
            onNavigateToSignUp = { navController.navigate(AuthRoute.SignUp(args.songId)) },
            onLoginSuccess = onLoginSuccess
        )
    }
}
