package io.alecrz.kmpmovies.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

import io.alecrz.kmpmovies.domain.model.Movie
import io.alecrz.kmpmovies.ui.common.ShimmerBox
import io.alecrz.kmpmovies.ui.screens.Screen
import kmpmoviesoficial.composeapp.generated.resources.Res
import kmpmoviesoficial.composeapp.generated.resources.app_name
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onMovieClick: (Movie) -> Unit,
    onFavoritesClick: () -> Unit,
    onWatchlistClick: () -> Unit,
    vm: HomeViewModel
) {

    val gridState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()


    val shouldLoadMore by derivedStateOf {
        val lastVisibleItem = gridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
        val totalItems = gridState.layoutInfo.totalItemsCount
        totalItems > 0 && lastVisibleItem >= totalItems - 4
    }


    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore) {
            vm.loadMoreMovies()
        }
    }
    Screen {
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(Res.string.app_name)) },
                    actions = {
                        IconButton(onClick = onFavoritesClick) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Favoritos"
                            )
                        }

                        IconButton(onClick = onWatchlistClick) {
                            Icon(
                                imageVector = Icons.Default.Schedule,
                                contentDescription = "Ver mas tarde"
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)

        ) { padding ->
            val state = vm.state
           /*LoadingIndicator(enabled = state.loading)*/
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                OutlinedTextField(
                    value = state.query,
                    onValueChange = { query->
                        vm.onQueryChange(query)
                        coroutineScope.launch {
                            gridState.scrollToItem(0)
                        }
                    },
                    label = { Text("Buscar peliculas") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )

                state.error?.let { error->
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                if(state.loading && state.query.isBlank()) {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(120.dp),
                        contentPadding = PaddingValues(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.fillMaxSize()
                    ){
                        items(10){
                            MovieItemSkeleton()
                        }
                    }
                }else if (!state.loading && state.movies.isEmpty()) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = if (state.query.isBlank()) {
                                "No hay peliculas disponibles"
                            } else {
                                "No se encontraron resultados para tu busqueda"
                            },
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }

                } else {

                    LazyVerticalGrid(
                        state = gridState,
                        columns = GridCells.Adaptive(120.dp),
                        contentPadding = PaddingValues(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(state.movies, key = { it.id }) {
                            MovieItem(movie = it, onClick = { onMovieClick(it) })
                        }

                        if (state.isLoadingMore) {
                            item {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 20.dp)
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        CircularProgressIndicator()
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = "Cargando mas peliculas",
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun MovieItem(movie: Movie, onClick:()->Unit) {
    Column(
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        AsyncImage(
            model = movie.poster,
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2 / 3f)
                .clip(MaterialTheme.shapes.small)
        )

        Text(
            text = movie.title,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            if (movie.isFavorite){
                StatusChip(
                    text="Favorito",
                    containerColor= MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
            }

            if(movie.isWatchlist){
                StatusChip(
                    text = "Ver mas tarde",
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }
        }


    }
}

@Composable
fun MovieItemSkeleton() {
    Column{
        ShimmerBox(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2/3f)
        )

        ShimmerBox(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(0.8f)
                .height(16.dp)
        )

        ShimmerBox(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth(0.5f)
                .height(14.dp)
        )
    }
}


@Composable
private fun StatusChip(
    text: String,
    containerColor: androidx.compose.ui.graphics.Color,
    contentColor: androidx.compose.ui.graphics.Color
) {
    Surface(
        shape = MaterialTheme.shapes.small,
        color = containerColor,
        tonalElevation = 2.dp,
        modifier = Modifier.wrapContentWidth()
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = contentColor,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}