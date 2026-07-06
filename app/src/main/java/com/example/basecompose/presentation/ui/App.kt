package com.example.basecompose.presentation.ui


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.basecompose.presentation.navigation.AppNavHost
import com.example.basecompose.presentation.navigation.AppState

@Composable
fun MyApp() {
    val rootNavController = rememberNavController()

    AppNavHost(
        navController = rootNavController
    )
}

