package com.example.basecompose.presentation.screen.signup

import androidx.lifecycle.SavedStateHandle
import com.example.basecompose.base.mvi.MVI
import com.example.basecompose.base.mvi.mvi
import com.example.basecompose.base.screen.BaseViewModel
import com.example.basecompose.presentation.screen.mainscreen1.model.SongItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel(),
    MVI<SignUpContract.ViewState, SignUpContract.ViewEvent, SignUpContract.ViewEffect> by mvi(
        SignUpContract.ViewState.initial()
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

    override fun onAction(uiAction: SignUpContract.ViewEvent) {

    }
}