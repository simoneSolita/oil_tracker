package com.simonesolita.oiltracker.model

import com.google.gson.annotations.SerializedName

data class OilInfoItem (
    @SerializedName("Date")
    val date: String,

    @SerializedName("Price")
    val price: Double
)