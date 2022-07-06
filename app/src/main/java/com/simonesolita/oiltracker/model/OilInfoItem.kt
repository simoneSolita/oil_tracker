package com.simonesolita.oiltracker.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class OilInfoItem (
    @SerializedName("Date")
    val date: LocalDate,

    @SerializedName("Price")
    val price: Double
)