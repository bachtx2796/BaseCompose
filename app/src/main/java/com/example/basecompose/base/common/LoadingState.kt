package com.example.basecompose.base.common

sealed class LoadingState {

    object Idle : LoadingState()

    object Loading : LoadingState()

    object DismissLoading : LoadingState()

    data class Error(val message: String? = null) : LoadingState()
}