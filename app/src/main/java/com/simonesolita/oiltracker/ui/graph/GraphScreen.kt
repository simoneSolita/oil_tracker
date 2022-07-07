package com.simonesolita.oiltracker.ui.graph

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.simonesolita.oiltracker.R
import com.simonesolita.oiltracker.model.OilInfoItem
import com.simonesolita.oiltracker.ui.components.LineChart
import com.simonesolita.oiltracker.ui.components.LineGraph
import com.simonesolita.oiltracker.ui.components.SingleOilInfo
import com.simonesolita.oiltracker.ui.components.createDatePicker
import com.simonesolita.oiltracker.ui.main.MainActivity
import com.simonesolita.oiltracker.utils.getMaxOilPriceItem
import com.simonesolita.oiltracker.utils.getMinOilPrice
import com.simonesolita.oiltracker.utils.toFormattedString
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*


@ExperimentalCoroutinesApi
@Composable
fun graphScreenViewModel(): GraphViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        MainActivity.ViewModelFactoryProvider::class.java
    ).graphViewModelFactory()

    return viewModel(factory = GraphViewModel.provideFactory(factory))
}

@ExperimentalCoroutinesApi
@Composable
fun GraphScreen(
) {
    val viewModel = graphScreenViewModel()
    GraphContent(
        viewModel.fromDate,
        { viewModel.selectedFromDate(it) },
        viewModel.toDate,
        { viewModel.selectedToDate(it) },
        viewModel.maxFromDate,
        viewModel.maxToDate
    )
}

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun GraphContent(
    fromDate: Date,
    onFromDateChange: (Date) -> Unit,
    toDate: Date,
    onToDateChange: (Date) -> Unit,
    maxFromDate: Date,
    maxToDate: Date,
) {
    val viewModel = graphScreenViewModel()
    val state by viewModel.state.collectAsState()

    if (viewModel.isDownloading) {
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(align = Alignment.Center)
        )
    } else {
        if (viewModel.isErrorDownload) {
            InfoContent(
                message = stringResource(id = R.string.error_download)
            )
        } else {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(12.dp))

                DatePicker(
                    title = stringResource(id = R.string.select_date_from),
                    placeholder = stringResource(id = R.string.select_date_from),
                    value = fromDate.toFormattedString(),
                    actualDate = fromDate,
                    currentMinDate = maxFromDate,
                    currentMaxDate = toDate,
                    onValueChange = { onFromDateChange(it) })

                Spacer(modifier = Modifier.height(12.dp))

                DatePicker(
                    title = stringResource(id = R.string.select_date_to),
                    placeholder = stringResource(id = R.string.select_date_to),
                    value = toDate.toFormattedString(),
                    actualDate = toDate,
                    currentMinDate = fromDate,
                    currentMaxDate = maxToDate,
                    onValueChange = { onToDateChange(it) }
                )

                Spacer(modifier = Modifier.height(12.dp))

                when {
                    state.isEmpty() -> {
                        InfoContent(
                            message = stringResource(id = R.string.no_data_for_filter)
                        )
                    }
                    state.size == 1 -> {
                        FormRow(
                            title = stringResource(id = R.string.price_single_day)) {
                            SingleOilInfo(state.first())
                        }

                    }
                    else -> {
                        OilInfosContent(state = state)
                    }
                }
            }
        }
    }
}

@Composable
fun OilInfosContent(
    state: List<OilInfoItem>
) {
    FormRow(title = stringResource(id = R.string.graph_content)) {
//        LineChart(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(200.dp)
//                .padding(6.dp),
//            oilInfos = state
//        )
        LineGraph(state)
    }
    FormRow(title = stringResource(id = R.string.card_first_day)) {
        SingleOilInfo(state.first())
    }

    FormRow(title = stringResource(id = R.string.card_last_day)) {
        SingleOilInfo(state.last())
    }

    FormRow(title = stringResource(id = R.string.card_max_price)) {
        SingleOilInfo(getMaxOilPriceItem(state))
    }

    FormRow(title = stringResource(id = R.string.card_low_price)) {
        SingleOilInfo(getMinOilPrice(state))
    }
}

@Composable
private fun FormRow(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column {
        Text(
            modifier = Modifier.padding(6.dp),
            text = title,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        content()
    }
}

@Composable
fun DatePicker(
    title: String,
    placeholder: String,
    value: String,
    actualDate: Date,
    currentMinDate: Date?,
    currentMaxDate: Date?,
    onValueChange: (Date) -> Unit

) {
    val context = LocalContext.current

    FormRow(title = title) {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            maxLines = 1,
            placeholder = { Text(text = placeholder) },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.background
            ),
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = {
                    createDatePicker(
                        context,
                        actualDate,
                        { onValueChange(it) },
                        currentMinDate,
                        currentMaxDate
                    )
                }) {
                    Icon(
                        imageVector = Icons.Filled.Event,
                        contentDescription = ""
                    )
                }
            }
        )
    }
}

@Composable
fun InfoContent(
    message: String
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = message,
            color = Color.Blue,
            fontSize = MaterialTheme.typography.h3.fontSize,
            fontWeight = FontWeight.Bold
        )
    }
}