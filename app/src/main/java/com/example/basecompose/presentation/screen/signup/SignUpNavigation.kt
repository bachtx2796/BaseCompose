package com.example.basecompose.presentation.screen.signup

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.basecompose.ext.sharedGraphViewModel
import com.example.basecompose.presentation.navigation.AuthRoute
import com.example.basecompose.presentation.screen.signin.SigninContract
import com.example.basecompose.presentation.screen.signin.SigninViewModel

fun NavGraphBuilder.signUpScreen(
    navController: NavController,
    onBack: () -> Unit
) {
    composable<AuthRoute.SignUp> { backStackEntry ->
        val args = backStackEntry.toRoute<AuthRoute.SignUp>()
        val shareVM: SigninViewModel = backStackEntry.sharedGraphViewModel<SigninViewModel, AuthRoute>(
            navController = navController
        )

        LaunchedEffect(args.songId) {
            shareVM.onAction(SigninContract.ViewEvent.SetSong(args.songId))
        }

        SignUpScreen(onBack = onBack)
    }
}
