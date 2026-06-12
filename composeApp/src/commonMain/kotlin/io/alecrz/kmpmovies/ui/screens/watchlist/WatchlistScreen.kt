package io.alecrz.kmpmovies.ui.screens.watchlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.alecrz.kmpmovies.ui.common.LoadingIndicator
import io.alecrz.kmpmovies.ui.screens.Screen
import io.alecrz.kmpmovies.ui.screens.home.MovieItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatchlistScreen(
    vm: WatchlistViewModel,
    onMovieClick: (Int) -> Unit,
    onBack: () -> Unit
) {
    val state = vm.state

    Screen {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Ver mas tarde") },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = "Volver"
                            )
                        }
                    }
                )
            }
        ) { padding ->
            LoadingIndicator(
                enabled = state.loading,
                modifier = Modifier.padding(padding)
            )

            if (!state.loading && state.movies.isEmpty()) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    Text(
                        text = "Aun no tienes peliculas en la lista",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(120.dp),
                    contentPadding = PaddingValues(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    items(
                        state.movies,
                        key = { movie -> movie.id }) { movie ->
                        MovieItem(
                            movie = movie,
                            onClick = {
                                onMovieClick(movie.id)
                            }
                        )
                    }
                }
            }
        }
    }
}
