package com.example.basecompose.presentation.screen.signin

import androidx.lifecycle.SavedStateHandle
import com.example.basecompose.base.mvi.MVI
import com.example.basecompose.base.mvi.mvi
import com.example.basecompose.base.screen.BaseViewModel
import com.example.basecompose.presentation.screen.mainscreen1.model.SongItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SigninViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel(),
    MVI<SigninContract.ViewState, SigninContract.ViewEvent, SigninContract.ViewEffect> by mvi(
        SigninContract.ViewState.initial()
    ) {

    private val songId: Int = checkNotNull(savedStateHandle["songId"])

    init {
        updateUiState {
            copy(SongItem(
                id = songId.toLong(),
                name = "Song #$songId",
                url = "https://example.com/song/$songId"
            ))
        }
    }

    override fun onAction(uiAction: SigninContract.ViewEvent) {

    }
}