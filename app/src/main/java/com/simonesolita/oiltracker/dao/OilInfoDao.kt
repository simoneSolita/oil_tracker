package com.simonesolita.oiltracker.dao

import androidx.room.*
import com.simonesolita.oiltracker.model.OilInfoItem
import java.util.*

@Dao
interface OilInfoDao {

    @Transaction
    @Query("SELECT * FROM oil_info")
    fun getAllPrices(): List<OilInfoItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOilInfo(oilInfo: OilInfoItem)

    @Query("DELETE FROM oil_info")
    fun deleteOilInfo()

    @Transaction
    @Query("SELECT * FROM oil_info WHERE date >= :dateFrom AND date <= :dateTo")
    fun getPricesByDate(dateFrom: Date,dateTo : Date): List<OilInfoItem>
}