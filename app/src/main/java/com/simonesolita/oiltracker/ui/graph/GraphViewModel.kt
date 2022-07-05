package com.simonesolita.oiltracker.ui.graph

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.simonesolita.oiltracker.model.OilInfoItem
import com.simonesolita.oiltracker.repositories.OilInfoRepository
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    private val _state = MutableStateFlow(emptyList<OilInfoItem>())
    val state : StateFlow<List<OilInfoItem>>
    get() = _state

    init {
        viewModelScope.launch {
            val oilInfo = oilInfoRepository.getOilInfo()
            _state.value = oilInfo
        }
    }

}