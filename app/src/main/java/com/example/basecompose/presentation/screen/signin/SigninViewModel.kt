package com.example.basecompose.presentation.screen.signin

import com.example.basecompose.base.mvi.MVI
import com.example.basecompose.base.mvi.mvi
import com.example.basecompose.base.screen.BaseViewModel
import com.example.basecompose.presentation.screen.mainscreen1.model.SongItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SigninViewModel @Inject constructor() : BaseViewModel(),
    MVI<SigninContract.ViewState, SigninContract.ViewEvent, SigninContract.ViewEffect> by mvi(
        SigninContract.ViewState.initial()
    ) {

    override fun onAction(uiAction: SigninContract.ViewEvent) {
        when (uiAction) {
            is SigninContract.ViewEvent.SetSong -> {
                val songId = uiAction.songId
                updateUiState {
                    copy(
                        songs = SongItem(
                            id = songId.toLong(),
                            name = "Song #$songId",
                            url = "https://example.com/song/$songId"
                        )
                    )
                }
            }
        }
    }
}