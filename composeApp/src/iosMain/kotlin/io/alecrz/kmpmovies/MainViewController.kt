package io.alecrz.kmpmovies

import androidx.compose.ui.window.ComposeUIViewController
import io.alecrz.kmpmovies.data.database.getDatabaseBuilder

fun MainViewController() = ComposeUIViewController {
    val database = getDatabaseBuilder().build()
    App(database.moviesDao())
}