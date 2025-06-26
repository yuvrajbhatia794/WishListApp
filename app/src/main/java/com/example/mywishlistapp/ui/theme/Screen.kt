package com.example.mywishlistapp.ui.theme

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {

    @Serializable
    data object Home : Screen()

    @Serializable
    data class AddScreen (
        val id: Long = 0L
    ) : Screen()
}