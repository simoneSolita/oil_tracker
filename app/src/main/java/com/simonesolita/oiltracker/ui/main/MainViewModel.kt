package com.simonesolita.oiltracker.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simonesolita.oiltracker.repositories.OilInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val oilInfoRepository: OilInfoRepository
) : ViewModel() {

    suspend fun downloadOilInfoPrices() {
        withContext(Dispatchers.Default){
            oilInfoRepository.downloadOilInfo({ onErrorDownload() }, { onCorrectDownload() })
        }
    }


    //error
    var correctLoading: Boolean by mutableStateOf(false)
    var errorDownload: Boolean by mutableStateOf(false)
    var correctDownload: Boolean by mutableStateOf(false)

    suspend fun onCorrectLoading() {
        downloadOilInfoPrices()
    }

    fun onErrorDownload() {
        correctLoading = true
        errorDownload = true
    }

    fun onCorrectDownload() {
        correctLoading = true
        correctDownload = true
    }
}