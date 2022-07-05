package com.simonesolita.oiltracker.ui.graph

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.simonesolita.oiltracker.repositories.OilInfoRepository
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class GraphViewModel @AssistedInject constructor(
    private val oilInfoRepository: OilInfoRepository
) : ViewModel() {
    //Assisted Factory
    @AssistedFactory
    interface Factory {
        fun create(
        ): GraphViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun provideFactory(
            assistedFactory: Factory
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create() as T
            }
        }
    }


}