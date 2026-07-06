package com.example.basecompose.presentation.ui


import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.example.basecompose.R
import com.example.basecompose.presentation.navigation.AppState
import com.example.basecompose.presentation.navigation.MainTabRoute

private data class BottomTab(
    val route: Any,
    val icon: ImageVector,
    @StringRes val label: Int
)

private val bottomTabs = listOf(
    BottomTab(MainTabRoute.MainScreen1, Icons.Outlined.Home, R.string.main_screen_1),
    BottomTab(MainTabRoute.MainScreen2, Icons.Outlined.Search, R.string.main_screen_2),
    BottomTab(MainTabRoute.MainScreen3, Icons.Outlined.Person, R.string.main_screen_3)
)

@Composable
fun AppBottomBar(
    appState: AppState
) {
    NavigationBar {
        bottomTabs.forEach { tab ->
            NavigationBarItem(
                selected = appState.isTabSelected(tab.route),
                onClick = {
                    appState.navigateToTab(tab.route)
                },
                icon = {
                    Icon(
                        imageVector = tab.icon,
                        contentDescription = null
                    )
                },
                label = {
                    Text(text = stringResource(tab.label))
                }
            )
        }
    }
}
