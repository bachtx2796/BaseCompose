package com.example.basecompose.presentation.screen.mainscreen1


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.basecompose.presentation.screen.mainscreen1.model.SongItem

@Composable
fun MainScreen1Screen(
    navigateToDetail: (Int) -> Unit,
    navigateToSignIn: () -> Unit,
    viewModel: MainScreen1ViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) { // lắng nghe sự kiện từ viewmodel tại đây
        viewModel.sideEffect.collect { effect ->
            when (effect) {

                else -> {

                }
            }
        }
    }

    MainScreen1Content(
        uiState = uiState.value,
        navigateToDetail,
        navigateToSignIn
    )
}

@Composable
private fun MainScreen1Content(
    uiState: MainScreen1Contract.ViewState,
    navigateToDetail: (Int) -> Unit,
    navigateToSignIn: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        item {
            Button(
                onClick = navigateToSignIn,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Sign in")
            }
        }

        items(
            items = uiState.songs,
            key = { it.id }
        ) { song ->
            SongItemCard(
                song = song,
                selected = false,
                onClick = {
                    navigateToDetail.invoke(song.id.toInt())
                }
            )
        }
    }
}

@Composable
private fun SongItemCard(
    song: SongItem,
    selected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = song.name,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "ID: ${song.id}",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )

            Text(
                text = song.url,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 6.dp)
            )

            if (selected) {
                Text(
                    text = "Đang chọn",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}
