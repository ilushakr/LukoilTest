package com.example.lukoiltest.presentation.navigation

sealed class Screens(val route: String) {
    object ListScreen : Screens(route = LIST_SCREEN)
    object DetailScreen : Screens(route = DETAIL_SCREEN)
    object EpisodesScreen : Screens(route = EPISODES_SCREEN)

    fun withArgs(vararg args: String) =
        buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }

    companion object {
        private const val LIST_SCREEN = "LIST_SCREEN"
        private const val DETAIL_SCREEN = "DETAIL_SCREEN"
        private const val EPISODES_SCREEN = "EPISODES_SCREEN"
    }
}