package com.example.basecompose.presentation.screen.hdsd

import com.example.basecompose.domain.model.Hdsd

interface HdsdContract {

    sealed interface ViewEvent {
        object GetData : ViewEvent
    }

    data class ViewState(
        val isLoading: Boolean = false,
        val items: List<Hdsd> = emptyList()
    ) {
        companion object {
            fun initial() = ViewState()
        }
    }

    sealed interface ViewEffect
}
