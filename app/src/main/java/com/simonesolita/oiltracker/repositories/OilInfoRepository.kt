package com.simonesolita.oiltracker.repositories

import com.simonesolita.oiltracker.dao.OilInfoDao
import com.simonesolita.oiltracker.model.OilInfoItem
import com.simonesolita.oiltracker.retrofit.OilInfoAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import javax.inject.Inject

class OilInfoRepository @Inject constructor(
    private val oilInfoAPI: OilInfoAPI,
    private val oilInfoDao: OilInfoDao
) {

    fun downloadOilInfo(
        onErrorDownload: () -> Unit,
        onCorrectDownload: () -> Unit
    ) {
        val call: Call<List<OilInfoItem>> = oilInfoAPI.getOilInfo()
        call.enqueue(object : Callback<List<OilInfoItem>> {
            override fun onResponse(
                call: Call<List<OilInfoItem>>,
                response: Response<List<OilInfoItem>>
            ) {
                if (response.isSuccessful) {
                    //remove previous data
                    oilInfoDao.deleteOilInfo()

                    response.body()?.forEach {
                        //insert data inside DB
                        insertOilInfo(it)
                        onCorrectDownload()
                    }
                } else {
                    onErrorDownload()
                }
            }

            override fun onFailure(call: Call<List<OilInfoItem>>, t: Throwable) {
                onErrorDownload()
            }

        })
    }

    fun insertOilInfo(oilInfoItem: OilInfoItem) {
        oilInfoDao.insertOilInfo(oilInfoItem)
    }

    fun getOilInfosByRange(
        dateFrom: Date?,
        dateTo: Date?
    ) = selectQuery(dateFrom,dateTo)

    private fun selectQuery(dateFrom: Date?,
                            dateTo: Date?) : List<OilInfoItem> {
        if (dateFrom == null || dateTo == null){
            return oilInfoDao.getAllPrices()
        }

        return oilInfoDao.getPricesByDate(dateFrom,dateTo)
    }

}