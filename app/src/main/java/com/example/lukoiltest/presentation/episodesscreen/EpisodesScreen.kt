package com.example.lukoiltest.presentation.episodesscreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.domain.apiobjects.Episode
import com.example.lukoiltest.UiResult
import com.example.lukoiltest.presentation.detailscreen.DetailScreenEvent
import com.example.lukoiltest.presentation.utils.UiEvent
import com.example.lukoiltest.presentation.widgets.BoldNameString
import com.example.lukoiltest.presentation.widgets.ErrorWidget
import com.example.lukoiltest.presentation.widgets.ProgressWidget
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun EpisodesScreen(
    args: EpisodesScreenArgs,
    onNavigate: (UiEvent) -> Unit,
    viewModel: EpisodesViewModel = viewModel()
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
                    Text(text = "Episodes", color = Color.White)
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            viewModel.onEvent(EpisodesScreenEvent.OnBackClick)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                elevation = 2.dp
            )
        }
    ) {
        when (val state = viewModel.state.collectAsState().value) {
            is UiResult.Error -> {
                ErrorWidget {
                    viewModel.onEvent(EpisodesScreenEvent.Retry(args.characterId))
                }
            }

            is UiResult.Pending -> {
                ProgressWidget()
            }

            is UiResult.Success -> {
                EpisodesContent(state.data)
            }
        }
    }

    LaunchedEffect(args) {
        viewModel.getEpisodes(args.characterId)
    }
}

@Composable
private fun EpisodesContent(
    data: List<Episode>
) {
    LazyColumn(Modifier.fillMaxSize()) {
        items(data) { episode ->
            EpisodeRow(episode)
            Divider()
        }
    }
}

@Composable
private fun EpisodeRow(episode: Episode) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        BoldNameString("Episode name", episode.name)
        Spacer(modifier = Modifier.height(8.dp))
        BoldNameString("Episode num", episode.episode)
    }
}