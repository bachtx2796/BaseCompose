package com.example.basecompose.presentation.screen.mainscreen2

import com.example.basecompose.base.mvi.MVI
import com.example.basecompose.base.mvi.mvi
import com.example.basecompose.base.screen.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainScreen2ViewModel @Inject constructor() : BaseViewModel(),
    MVI<MainScreen2Contract.ViewState, MainScreen2Contract.ViewEvent, MainScreen2Contract.ViewEffect> by mvi(
        MainScreen2Contract.ViewState.initial()
    ) {

    override fun onAction(uiAction: MainScreen2Contract.ViewEvent) {
        when (uiAction) {
            MainScreen2Contract.ViewEvent.GetData -> {

            }
        }
    }
}
