package com.simonesolita.oiltracker.retrofit

import com.simonesolita.oiltracker.model.OilInfoItem
import retrofit2.http.GET

interface OilInfoAPI {
    @GET(APIConstants.ENDPOINT_OIL_INFO)
    suspend fun getOilInfo(): List<OilInfoItem>
}