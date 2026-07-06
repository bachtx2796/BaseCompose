package com.example.basecompose.presentation.screen.mainscreen1

import com.example.basecompose.presentation.screen.mainscreen1.model.SongItem

interface MainScreen1Contract {

    sealed interface ViewEvent {
        object GetData : ViewEvent

        data class ClickSong(val songItem: SongItem) : ViewEvent
    }

    data class ViewState(val songs: List<SongItem> = emptyList()) {
        companion object {
            fun initial() = ViewState()
        }
    }

    sealed interface ViewEffect
}
