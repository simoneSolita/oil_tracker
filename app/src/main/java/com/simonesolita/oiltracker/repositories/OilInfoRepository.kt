package com.simonesolita.oiltracker.repositories

import com.simonesolita.oiltracker.model.OilInfoItem
import com.simonesolita.oiltracker.retrofit.OilInfoAPI
import javax.inject.Inject
import javax.inject.Singleton

class OilInfoRepository @Inject constructor(
    private val oilInfoAPI: OilInfoAPI
) {

    suspend fun downloadOilInfo(): List<OilInfoItem> {
        return oilInfoAPI.getOilInfo()
    }
}