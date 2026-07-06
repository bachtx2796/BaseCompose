package com.example.basecompose.presentation.screen.mainscreen3

interface MainScreen3Contract {

    sealed interface ViewEvent {
        object GetData : ViewEvent
    }

    data class ViewState(val title: String = "MainScreen3") {
        companion object {
            fun initial() = ViewState()
        }
    }

    sealed interface ViewEffect
}
