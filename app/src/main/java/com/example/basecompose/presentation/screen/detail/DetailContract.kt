package com.example.basecompose.presentation.screen.detail

import com.example.basecompose.presentation.screen.mainscreen1.model.SongItem

interface DetailContract {

    sealed interface ViewEvent {
    }

    data class ViewState(val songs: SongItem?) {
        companion object {
            fun initial() = ViewState(null)
        }
    }

    sealed interface ViewEffect
}