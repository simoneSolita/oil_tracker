package com.simonesolita.oiltracker.repositories

import com.simonesolita.oiltracker.model.OilInfo
import com.simonesolita.oiltracker.retrofit.OilInfoAPI
import javax.inject.Inject


class OilInfoRepository @Inject constructor(
    private val oilInfoAPI: OilInfoAPI
) {
    suspend fun getOilInfo(): OilInfo {
        return oilInfoAPI.getOilInfo()
    }
}