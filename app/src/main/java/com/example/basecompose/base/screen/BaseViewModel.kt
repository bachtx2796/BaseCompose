package com.example.basecompose.base.screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.basecompose.base.common.LoadingState
import com.example.basecompose.base.common.MutableLiveEvent
import kotlinx.coroutines.Job

abstract class BaseViewModel : ViewModel() {

    protected val _loadingLiveData: MutableLiveEvent<LoadingState> = MutableLiveEvent()
    val loadingLiveData: LiveData<LoadingState> get() = _loadingLiveData


    fun setLoadingState(state: LoadingState) {
        _loadingLiveData.postValue(state)
    }

    fun isApiLoading(): Boolean {
        return _loadingLiveData.value == LoadingState.Loading
    }

    fun showLoading() {
        setLoadingState(LoadingState.Loading)
    }

    fun hideLoading() {
        setLoadingState(LoadingState.DismissLoading)
    }
}