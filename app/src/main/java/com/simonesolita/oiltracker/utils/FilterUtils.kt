package com.simonesolita.oiltracker.utils

import com.simonesolita.oiltracker.model.OilInfoItem
import java.time.LocalDate

fun buildDatePredicate(dateFrom: LocalDate?, dateTo: LocalDate?): (OilInfoItem) -> Boolean {
    if (dateFrom != null && dateTo == null) {
        return { oilInfoItem ->
            oilInfoItem.date.isAfter(dateFrom.removeOneDay())
        }
    }
    if (dateTo != null && dateFrom == null) {
        return { oilInfoItem ->
            oilInfoItem.date.isBefore(dateTo.addOneDay())
        }
    }
    if (dateTo != null && dateFrom != null) {
        return { oilInfoItem ->
            oilInfoItem.date.isBefore(dateTo.addOneDay()) &&
                    oilInfoItem.date.isAfter(dateFrom.removeOneDay())
        }
    }

    return { true }
}

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