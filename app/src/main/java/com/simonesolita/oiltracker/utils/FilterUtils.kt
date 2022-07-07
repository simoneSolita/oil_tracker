package com.simonesolita.oiltracker.utils

import com.simonesolita.oiltracker.model.OilInfoItem

fun getMaxOilPrice(
    oilInfos: List<OilInfoItem>
): Double? {
    val arrayPrices = oilInfos.map { it.price }.toTypedArray()
    return arrayPrices.maxOrNull()
}

fun getMaxOilPriceItem(
    oilInfos: List<OilInfoItem>
): OilInfoItem {
    val arrayPrices = oilInfos.map { it.price }.toTypedArray()
    return oilInfos[arrayPrices.indexOf(arrayPrices.maxOrNull())]
}

fun getMinOilPrice(
    oilInfos: List<OilInfoItem>
): OilInfoItem {
    val arrayPrices = oilInfos.map { it.price }.toTypedArray()
    return oilInfos[arrayPrices.indexOf(arrayPrices.minOrNull())]
}