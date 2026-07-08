package com.example.basecompose.ext

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController

/**
 * Lấy [ViewModel] được scope theo graph cha [graphRoute] (dùng chung cho mọi màn
 * trong cùng graph). Phải gọi bên trong lambda của `composable { backStackEntry -> }`.
 */
@Composable
inline fun <reified VM : ViewModel, reified Graph : Any> NavBackStackEntry.sharedGraphViewModel(
    navController: NavController,
): VM {
    val parentEntry = remember(this) {
        navController.getBackStackEntry<Graph>()
    }
    return hiltViewModel<VM>(parentEntry)
}
