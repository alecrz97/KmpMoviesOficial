package io.alecrz.kmpmovies



import androidx.compose.runtime.*
import coil3.ImageLoader
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import coil3.request.crossfade
import coil3.util.DebugLogger
import io.alecrz.kmpmovies.data.database.MoviesDao
import io.alecrz.kmpmovies.ui.screens.Navigation
import org.jetbrains.compose.ui.tooling.preview.Preview


@OptIn(ExperimentalCoilApi::class)
@Composable
@Preview
fun App(moviesDao: MoviesDao) {
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .crossfade(true)
            .logger(DebugLogger())
            .build()

    }

   Navigation(moviesDao)
    }

