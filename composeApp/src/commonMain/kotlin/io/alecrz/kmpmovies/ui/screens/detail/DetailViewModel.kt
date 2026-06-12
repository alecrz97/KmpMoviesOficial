package io.alecrz.kmpmovies.ui.screens.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.alecrz.kmpmovies.domain.model.Movie
import io.alecrz.kmpmovies.domain.usecase.GetMovieByIdUseCase
import io.alecrz.kmpmovies.domain.usecase.ToggleFavoriteUseCase
import io.alecrz.kmpmovies.domain.usecase.ToggleWatchlistUseCase
import kotlinx.coroutines.launch

class DetailViewModel(
    private val id: Int,
    private val getMovieByIdUseCase: GetMovieByIdUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val toggleWatchlistUseCase: ToggleWatchlistUseCase
) : ViewModel() {

    var state by mutableStateOf(UiState())
        private set

    init {
        viewModelScope.launch {
            state = UiState(loading = true)
            getMovieByIdUseCase(id).collect { movie ->
                movie?.let { state = UiState(loading = false, movie = it) }
            }
        }
    }

    fun onFavoriteClick() {
        val movie = state.movie ?: return
        viewModelScope.launch {
            toggleFavoriteUseCase(movie.id, !movie.isFavorite)
        }
    }

    fun onWatchlistClick() {
        val movie = state.movie ?: return
        viewModelScope.launch {
            toggleWatchlistUseCase(movie.id, !movie.isWatchlist)
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val movie: Movie? = null
    )
}