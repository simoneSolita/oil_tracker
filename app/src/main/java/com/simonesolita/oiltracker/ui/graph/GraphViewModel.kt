package com.simonesolita.oiltracker.ui.graph

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
import java.util.*

class GraphViewModel @AssistedInject constructor(
    private val oilInfoRepository: OilInfoRepository
) : ViewModel() {
    //Assisted Factory
    @AssistedFactory
    interface Factory {
        fun create(
        ): GraphViewModel
    }

    var oilInfos = emptyList<OilInfoItem>()

    var fromDate by mutableStateOf(Date())
    var toDate by mutableStateOf(Date())

    var maxFromDate by mutableStateOf(Date())
    var maxToDate by mutableStateOf(Date())

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
    val state: StateFlow<List<OilInfoItem>>
        get() = _state


    init {
        viewModelScope.launch {

            //get all infos
            oilInfos = oilInfoRepository.getOilInfosByRange(null,null)

            //initialize datepickers to first and last day possible
            fromDate = oilInfos.first().date
            toDate = oilInfos.last().date

            //saving max and min date selectable in datepickers
            maxFromDate = fromDate
            maxToDate = toDate

            _state.value = oilInfos
        }
    }

    fun selectedFromDate(date: Date) {
        fromDate = date
        recalculateOilInfos()
    }

    fun selectedToDate(date: Date) {
        toDate = date
        recalculateOilInfos()
    }

    fun recalculateOilInfos(){
        oilInfos = oilInfoRepository.getOilInfosByRange(fromDate,toDate)
        _state.value = oilInfos
    }

}