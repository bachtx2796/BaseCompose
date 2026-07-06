package com.example.basecompose.presentation.screen.mainscreen2

interface MainScreen2Contract {

    sealed interface ViewEvent {
        object GetData : ViewEvent
    }

    data class ViewState(val title: String = "MainScreen2") {
        companion object {
            fun initial() = ViewState()
        }
    }

    sealed interface ViewEffect
}
