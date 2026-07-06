package com.example.basecompose.presentation.screen.signin

import com.example.basecompose.presentation.screen.mainscreen1.model.SongItem

interface SigninContract {

    sealed interface ViewEvent {
    }

    data class ViewState(val songs: SongItem?) {
        companion object {
            fun initial() = ViewState(null)
        }
    }

    sealed interface ViewEffect
}