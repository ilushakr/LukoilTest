package com.example.lukoiltest.presentation.listscreen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.domain.apiobjects.CharacterListItem
import com.example.lukoiltest.R
import com.example.lukoiltest.UiResult
import com.example.lukoiltest.presentation.utils.UiEvent
import com.example.lukoiltest.presentation.widgets.ErrorWidget
import com.example.lukoiltest.presentation.widgets.ProgressWidget
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ListScreen(
    onNavigate: (UiEvent) -> Unit,
    viewModel: ListViewModel = viewModel()
) {

    LaunchedEffect(true) {
        viewModel.uiEvent.collectLatest { event ->
            onNavigate(event)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Characters", color = Color.White)
                },
                elevation = 2.dp
            )
        }
    ) {
        when (val state = viewModel.state.collectAsState().value) {
            is UiResult.Error -> {
                ErrorWidget {
                    viewModel.onEvent(ListScreenEvent.Retry)
                }
            }

            is UiResult.Pending -> {
                ProgressWidget()
            }

            is UiResult.Success -> {
                if (state.data.isNotBlank()) {
                    val items = remember(state.data) {
                        viewModel.getPager(state.data)
                    }.collectAsLazyPagingItems()

                    LazyColumn(Modifier.fillMaxSize()) {
                        items(items) { item ->
                            item?.let {
                                CharacterItem(it) {
                                    viewModel.onEvent(ListScreenEvent.OpenDetailScreen(item.id))
                                }
                                Divider()
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun CharacterItem(item: CharacterListItem, onCLick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(onClick = onCLick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            model = item.image,
            contentDescription = null,
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = item.name)
    }
}