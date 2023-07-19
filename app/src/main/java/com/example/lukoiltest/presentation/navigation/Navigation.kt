package com.example.lukoiltest.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lukoiltest.presentation.detailscreen.DetailScreenArgs
import com.example.lukoiltest.presentation.listscreen.ListScreen
import com.example.lukoiltest.presentation.detailscreen.DetailScreen
import com.example.lukoiltest.presentation.utils.UiEvent
import com.example.lukoiltest.fromArgs
import com.example.lukoiltest.presentation.episodesscreen.EpisodesScreen
import com.example.lukoiltest.presentation.episodesscreen.EpisodesScreenArgs

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.ListScreen.route) {
        composable(route = Screens.ListScreen.route) {
            ListScreen(
                onNavigate = { event ->
                    when (event) {
                        is UiEvent.Navigate -> navController.navigate(event.route)
                        else -> Unit
                    }
                }
            )
        }
        composable(
            route = "${Screens.DetailScreen.route}/{${DetailScreenArgs.ARGS}}",
            arguments = listOf(
                navArgument(DetailScreenArgs.ARGS) {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            entry.arguments?.getString(DetailScreenArgs.ARGS)?.let { detailArgs ->
                DetailScreen(
                    args = fromArgs(detailArgs),
                    onNavigate = { event ->
                        when (event) {
                            is UiEvent.PopBackStack -> navController.popBackStack()
                            is UiEvent.Navigate -> navController.navigate(event.route)
                        }
                    }
                )
            }
        }

        composable(
            route = "${Screens.EpisodesScreen.route}/{${EpisodesScreenArgs.ARGS}}",
            arguments = listOf(
                navArgument(EpisodesScreenArgs.ARGS) {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            entry.arguments?.getString(EpisodesScreenArgs.ARGS)?.let { episodesArgs ->
                EpisodesScreen(
                    args = fromArgs(episodesArgs),
                    onNavigate = { event ->
                        when (event) {
                            is UiEvent.PopBackStack -> navController.popBackStack()
                            else -> Unit
                        }
                    }
                )
            }
        }
    }
}