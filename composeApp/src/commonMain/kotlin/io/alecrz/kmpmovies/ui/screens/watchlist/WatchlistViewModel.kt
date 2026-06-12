package io.alecrz.kmpmovies.ui.screens.watchlist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.alecrz.kmpmovies.domain.model.Movie
import io.alecrz.kmpmovies.domain.usecase.GetWatchlistMoviesUseCase
import kotlinx.coroutines.launch

class WatchlistViewModel(
    private val getWatchlistMoviesUseCase: GetWatchlistMoviesUseCase
) : ViewModel() {

    var state by mutableStateOf(UiState())
        private set

    init {
        viewModelScope.launch {
            state = UiState(loading = true)
            getWatchlistMoviesUseCase().collect { movies ->
                state = UiState(
                    loading = false, movies = movies
                )
            }
        }
    }


    data class UiState(
        val loading: Boolean = false,
        val movies: List<Movie> = emptyList()
    )
}

