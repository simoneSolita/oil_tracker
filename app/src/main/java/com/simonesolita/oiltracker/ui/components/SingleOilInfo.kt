package com.simonesolita.oiltracker.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.simonesolita.oiltracker.R
import com.simonesolita.oiltracker.model.OilInfoItem
import com.simonesolita.oiltracker.utils.toLocalDate
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*

@Composable
fun SingleOilInfo(
    oilInfo: OilInfoItem,
) {
    val paddingModifier = Modifier.padding(10.dp).fillMaxWidth()

    Card(
        modifier = paddingModifier,
        elevation = 10.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = paddingModifier) {
            Text(
                text = String.format(stringResource(id = R.string.card_data), oilInfo.date.toString()),
                style = MaterialTheme.typography.h5
            )

            Text(
                text = String.format(stringResource(id = R.string.card_price), oilInfo.price.toString()),
                style = MaterialTheme.typography.h5
            )
        }
    }
}

@Preview
@OptIn(ExperimentalCoroutinesApi::class)
@Composable
private fun SingleOilInfoPreview() {
    SingleOilInfo(OilInfoItem(Date().toLocalDate(), 18.2))
}