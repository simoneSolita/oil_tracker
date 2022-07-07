package com.simonesolita.oiltracker.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.madrapps.plot.line.DataPoint
import com.madrapps.plot.line.LineGraph
import com.madrapps.plot.line.LinePlot
import com.simonesolita.oiltracker.model.OilInfoItem

@Composable
fun LineGraph(oilInfos: List<OilInfoItem>) {
    val lines = toDataPoint(oilInfos)
    LineGraph(
        plot = LinePlot(
            listOf(
                LinePlot.Line(
                    lines[0],
                    LinePlot.Connection(color = Color.Red),
                    LinePlot.Intersection(color = Color.Red),
                    LinePlot.Highlight(color = Color.Yellow),
                )
            ),
            grid = LinePlot.Grid(Color.Red, steps = 4),
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        onSelection = { xLine, points ->
            // Do whatever you want here
        }
    )
}

fun toDataPoint(oilInfos: List<OilInfoItem>): List<List<DataPoint>> {
    val dataPoints = arrayListOf<DataPoint>()
    oilInfos.forEach{oilInfoItem ->
        dataPoints.add(DataPoint(oilInfos.indexOf(oilInfoItem).toFloat(),oilInfoItem.price.toFloat()))
    }
    return listOf(dataPoints)
}