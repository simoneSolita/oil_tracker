package com.simonesolita.oiltracker.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
) : ViewModel() {
    //error
    var correctLoading: Boolean by mutableStateOf(false)
    var correctDownload: Boolean by mutableStateOf(false)
    var errorDownload: Boolean by mutableStateOf(false)

    fun onCorrectLoading() {
        correctLoading = true
        correctDownload = true
    }
}