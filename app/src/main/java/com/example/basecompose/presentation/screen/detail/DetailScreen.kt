package com.example.basecompose.presentation.screen.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun DetailScreen(
    onBack: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    DetailContent(
        uiState = uiState.value,
        onBack = onBack
    )
}

@Composable
private fun DetailContent(
    uiState: DetailContract.ViewState,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Detail Screen",
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = "Song ID: ${uiState.songs?.id}",
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = "Song Name: ${uiState.songs?.name}",
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = "Song URL: ${uiState.songs?.url}",
            style = MaterialTheme.typography.bodyLarge
        )

        Button(onClick = onBack) {
            Text("Back")
        }
    }
}