package com.example.basecompose.presentation.screen.detail

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.example.basecompose.base.mvi.MVI
import com.example.basecompose.base.mvi.mvi
import com.example.basecompose.base.screen.BaseViewModel
import com.example.basecompose.presentation.navigation.RootRoute
import com.example.basecompose.presentation.screen.mainscreen1.model.SongItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel(),
    MVI<DetailContract.ViewState, DetailContract.ViewEvent, DetailContract.ViewEffect> by mvi(
        DetailContract.ViewState.initial()
    ) {

    private val route = savedStateHandle.toRoute<RootRoute.Detail>()

    val songId: Int = route.songId

    init {
        updateUiState {
            copy(SongItem(
                id = songId.toLong(),
                name = "Song #$songId",
                url = "https://example.com/song/$songId"
            ))
        }
    }

    override fun onAction(uiAction: DetailContract.ViewEvent) {

    }
}
