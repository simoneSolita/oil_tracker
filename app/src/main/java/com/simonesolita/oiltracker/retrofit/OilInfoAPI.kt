package com.simonesolita.oiltracker.retrofit

import com.simonesolita.oiltracker.model.OilInfoItem
import retrofit2.Call
import retrofit2.http.GET

interface OilInfoAPI {
    @GET(APIConstants.ENDPOINT_OIL_INFO)
    fun getOilInfo(): Call<List<OilInfoItem>>
}