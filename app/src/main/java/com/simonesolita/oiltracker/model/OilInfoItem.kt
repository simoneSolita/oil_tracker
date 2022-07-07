package com.simonesolita.oiltracker.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(tableName = "oil_info")
data class OilInfoItem(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "date")
    @SerializedName("Date")
    val date: Date,

    @ColumnInfo(name = "price")
    @SerializedName("Price")
    val price: Double
)