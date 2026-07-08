package com.example.basecompose.presentation.navigation


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.basecompose.presentation.screen.detail.detailScreen
import com.example.basecompose.presentation.screen.detail.navigateToDetail
import com.example.basecompose.presentation.screen.hdsd.hdsdScreen
import com.example.basecompose.presentation.screen.hdsd.navigateToHdsd
import com.example.basecompose.presentation.screen.mainscreen1.mainScreen1
import com.example.basecompose.presentation.screen.mainscreen2.mainScreen2
import com.example.basecompose.presentation.screen.mainscreen3.mainScreen3
import com.example.basecompose.presentation.screen.auth.navigateToAuth
import com.example.basecompose.presentation.screen.signin.signInScreen
import com.example.basecompose.presentation.screen.signup.signUpScreen
import com.example.basecompose.presentation.ui.AppBottomBar

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = RootRoute.Main,
        modifier = modifier
    ) {
        composable<RootRoute.Main> {
            MainScreenContainer(
                navigateToDetail = { songId ->
                    navController.navigateToDetail(songId)
                },
                navigateToSignin = { songId ->
                    // một lối vào duy nhất — route data quyết định start ở đâu
                    navController.navigateToAuth(songId, start = AuthStart.SignIn)
                    // muốn mở thẳng SignUp: navigateToAuth(songId, AuthStart.SignUp)
                },
                navigateToHdsd = {
                    navController.navigateToHdsd()
                }
            )
        }

        detailScreen(
            onBack = navController::popBackStack
        )

        hdsdScreen(
            onBack = navController::popBackStack
        )

        navigation<AuthRoute>(
            startDestination = AuthRoute.SignIn::class
        ) {

            signInScreen(
                navController = navController,
                onLoginSuccess = {
                    navController.navigate(RootRoute.Main) {
                        popUpTo<AuthRoute> {
                            inclusive = true
                        }
                    }
                }
            )

            signUpScreen(
                navController = navController,
                onBack = { navController.popBackStack() }
            )
        }
    }
}

@Composable
fun MainScreenContainer(
    navigateToDetail: (Int) -> Unit,
    navigateToSignin: (Int) -> Unit,
    navigateToHdsd: () -> Unit
) {
    val tabNavController = rememberNavController()
    val appState = remember(tabNavController) { AppState(tabNavController) }

    Scaffold(
        bottomBar = {
            AppBottomBar(appState = appState)
        }
    ) { innerPadding ->
        NavHost(
            navController = tabNavController,
            startDestination = MainTabRoute.MainScreen1,
            modifier = Modifier.padding(innerPadding)
        ) {
            mainScreen1(
                navigateToDetail = navigateToDetail,
                navigateToSignIn = navigateToSignin,
                navigateToHdsd = navigateToHdsd
            )

            mainScreen2()

            mainScreen3()
        }
    }
}
