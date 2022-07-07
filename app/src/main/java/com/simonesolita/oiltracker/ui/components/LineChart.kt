package com.simonesolita.oiltracker.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.Stroke
import com.simonesolita.oiltracker.model.OilInfoItem
import com.simonesolita.oiltracker.utils.getMaxOilPrice

@Composable
fun LineChart(
    modifier: Modifier = Modifier,
    oilInfos: List<OilInfoItem>,
) {
    if (oilInfos.isEmpty()) return

    Canvas(modifier = modifier) {
        // Total number of infos.
        val totalRecords = oilInfos.size

        // Maximum distance between dots (price)
        val lineDistance = size.width / (totalRecords + 1)

        // Canvas height
        val cHeight = size.height

        // Add some kind of a "Padding" for the initial point where the line starts.
        var currentLineDistance = 0F + lineDistance

        val maxOilPrice = getMaxOilPrice(oilInfos = oilInfos)

        oilInfos.forEachIndexed { index, oilInfo ->
            if (totalRecords >= index + 2) {
                drawLine(
                    start = Offset(
                        x = currentLineDistance,
                        y = calculateYCoordinate(
                            higherOilPrice = maxOilPrice,
                            currentOilPrice = oilInfo.price,
                            canvasHeight = cHeight
                        )
                    ),
                    end = Offset(
                        x = currentLineDistance + lineDistance,
                        y = calculateYCoordinate(
                            higherOilPrice = maxOilPrice,
                            currentOilPrice = oilInfos[index + 1].price,
                            canvasHeight = cHeight
                        )
                    ),
                    color = androidx.compose.ui.graphics.Color.Black,
                    strokeWidth = Stroke.DefaultMiter
                )
            }
            currentLineDistance += lineDistance
        }
    }
}

private fun calculateYCoordinate(
    higherOilPrice: Double?,
    currentOilPrice: Double,
    canvasHeight: Float
): Float {
    val maxAndCurrentValueDifference = (higherOilPrice?.minus(currentOilPrice))
        ?.toFloat()
    val relativePercentageOfScreen = (canvasHeight / higherOilPrice!!)
        .toFloat()
    if (maxAndCurrentValueDifference != null) {
        return maxAndCurrentValueDifference * relativePercentageOfScreen
    }
    return Float.NaN
}