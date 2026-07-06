package com.example.basecompose.presentation.screen.mainscreen1

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import com.example.basecompose.base.mvi.MVI
import com.example.basecompose.base.mvi.mvi
import com.example.basecompose.base.screen.BaseViewModel
import com.example.basecompose.presentation.screen.mainscreen1.model.SongItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class MainScreen1ViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    @ApplicationContext private val context: Context
) : BaseViewModel(),
    MVI<MainScreen1Contract.ViewState, MainScreen1Contract.ViewEvent, MainScreen1Contract.ViewEffect> by mvi(
        MainScreen1Contract.ViewState.initial()
    ) {

    init {
        onAction(MainScreen1Contract.ViewEvent.GetData)
    }

    override fun onAction(uiAction: MainScreen1Contract.ViewEvent) {
        when (uiAction) {
            MainScreen1Contract.ViewEvent.GetData -> {
                updateUiState {
                    copy(
                        songs = getSongs()
                    )
                }
            }

            else -> {

            }
        }
    }

    fun getSongs(): List<SongItem> {
        val json = context.assets
            .open("infodata.json")
            .bufferedReader()
            .use { it.readText() }

        val type = object : TypeToken<List<SongItem>>() {}.type
        return Gson().fromJson(json, type)
    }
}
