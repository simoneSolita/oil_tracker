package com.simonesolita.oiltracker.ui.graph

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.simonesolita.oiltracker.model.OilInfoItem
import com.simonesolita.oiltracker.ui.main.MainActivity
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.ExperimentalCoroutinesApi



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
    navController: NavController
) {
    val viewModel = graphScreenViewModel()
    val state by viewModel.state.collectAsState()

    LazyColumn {
        if (state.isEmpty()) {
            item {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(align = Alignment.Center)
                )
            }
        }

        items(state) { info: OilInfoItem ->
            OilInfoCard(info = info)
        }
    }
}

@Composable
fun OilInfoCard(info: OilInfoItem) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.padding(14.dp)
    ) {
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = info.date,
                color = Color.Black,
                fontSize = MaterialTheme.typography.h3.fontSize,
                fontWeight = FontWeight.Bold
            )
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = info.price.toString(),
                color = Color.Black,
                fontSize = MaterialTheme.typography.h3.fontSize,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

