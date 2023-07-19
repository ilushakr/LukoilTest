package com.example.lukoiltest.presentation.detailscreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.domain.apiobjects.CharacterDetailItem
import com.example.domain.apiobjects.CharacterListItem
import com.example.lukoiltest.UiResult
import com.example.lukoiltest.presentation.listscreen.ListScreenEvent
import com.example.lukoiltest.presentation.utils.UiEvent
import com.example.lukoiltest.presentation.widgets.BoldNameString
import com.example.lukoiltest.presentation.widgets.ErrorWidget
import com.example.lukoiltest.presentation.widgets.ProgressWidget
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailScreen(
    args: DetailScreenArgs,
    onNavigate: (UiEvent) -> Unit,
    viewModel: DetailViewModel = viewModel()
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
                    Text(text = "Details", color = Color.White)
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            viewModel.onEvent(DetailScreenEvent.OnBackClick)
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
                    viewModel.onEvent(DetailScreenEvent.Retry(args.id))
                }
            }

            is UiResult.Pending -> {
                ProgressWidget()
            }

            is UiResult.Success -> {
                DetailContent(state.data) { characterId ->
                    viewModel.onEvent(DetailScreenEvent.OpenEpisodes(characterId))
                }
            }
        }
    }

    LaunchedEffect(true) {
        viewModel.getCharacter(args.id)
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun DetailContent(data: CharacterDetailItem, watchEpisodes: (Int) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            GlideImage(
                model = data.image,
                contentDescription = null
            )

            Column(Modifier.fillMaxWidth()) {
                BoldNameString("Name", data.name)
                BoldNameString("Status", data.status)
                BoldNameString("Species", data.species)
                BoldNameString("Location", data.location.name)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = {
                watchEpisodes(data.id)
            }
        ) {
            Text(text = "Watch episoded")
        }
    }
}
