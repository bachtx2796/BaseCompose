package com.example.basecompose.presentation.screen.hdsd

import androidx.lifecycle.viewModelScope
import com.example.basecompose.base.mvi.MVI
import com.example.basecompose.base.mvi.mvi
import com.example.basecompose.base.screen.BaseViewModel
import com.example.basecompose.domain.usecase.HDSDUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HdsdViewModel @Inject constructor(
    private val hdsdUseCase: HDSDUseCase
) : BaseViewModel(),
    MVI<HdsdContract.ViewState, HdsdContract.ViewEvent, HdsdContract.ViewEffect> by mvi(
        HdsdContract.ViewState.initial()
    ) {

    init {
        onAction(HdsdContract.ViewEvent.GetData)
    }

    override fun onAction(uiAction: HdsdContract.ViewEvent) {
        when (uiAction) {
            HdsdContract.ViewEvent.GetData -> getData()
        }
    }

    private fun getData() {
        viewModelScope.launch {
            hdsdUseCase.get()
                .onStart { updateUiState { copy(isLoading = true) } }
                .collect { items ->
                    updateUiState { copy(isLoading = false, items = items) }
                }
        }
    }
}
