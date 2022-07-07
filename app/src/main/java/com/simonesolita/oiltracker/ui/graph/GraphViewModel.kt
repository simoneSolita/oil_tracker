package com.simonesolita.oiltracker.ui.graph

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.simonesolita.oiltracker.model.OilInfoItem
import com.simonesolita.oiltracker.repositories.OilInfoRepository
import com.simonesolita.oiltracker.utils.buildDatePredicate
import com.simonesolita.oiltracker.utils.toDate
import com.simonesolita.oiltracker.utils.toLocalDate
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

    var oilInfos: List<OilInfoItem> = emptyList()

    var fromDate by mutableStateOf(Date())
    var toDate by mutableStateOf(Date())

    var maxFromDate by mutableStateOf(Date())
    var maxToDate by mutableStateOf(Date())

    var isDownloading: Boolean by mutableStateOf(true)
    var isErrorDownload: Boolean by mutableStateOf(false)

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
            //download json
            try {
                val oilInfo = oilInfoRepository.downloadOilInfo()
                isDownloading = false

                //assign to a variable the json content
                oilInfos = oilInfo

                //initialize datepickers to first and last day possible
                fromDate = oilInfos.first().date.toDate()
                toDate = oilInfos.last().date.toDate()

                //saving max and min date selectable in datepickers
                maxFromDate = fromDate
                maxToDate = toDate

                //filter using datepicker values
                filterOilInfos()
            }catch (ex : Exception){
                println(ex.localizedMessage)
                isDownloading = false
                isErrorDownload = true
            }
        }
    }

    fun selectedFromDate(date: Date) {
        fromDate = date
        filterOilInfos()
    }

    fun selectedToDate(date: Date) {
        toDate = date
        filterOilInfos()
    }

    fun filterOilInfos() {
        //filter and assign
        _state.value =
            oilInfos.filter(buildDatePredicate(fromDate.toLocalDate(), toDate.toLocalDate()))
    }

}