package com.example.basecompose.presentation.screen.mainscreen3

import com.example.basecompose.base.mvi.MVI
import com.example.basecompose.base.mvi.mvi
import com.example.basecompose.base.screen.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainScreen3ViewModel @Inject constructor() : BaseViewModel(),
    MVI<MainScreen3Contract.ViewState, MainScreen3Contract.ViewEvent, MainScreen3Contract.ViewEffect> by mvi(
        MainScreen3Contract.ViewState.initial()
    ) {

    override fun onAction(uiAction: MainScreen3Contract.ViewEvent) {
        when (uiAction) {
            MainScreen3Contract.ViewEvent.GetData -> {

            }
        }
    }
}
