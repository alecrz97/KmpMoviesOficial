package io.alecrz.kmpmovies.ui.screens.favorites

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.alecrz.kmpmovies.domain.model.Movie
import io.alecrz.kmpmovies.domain.usecase.GetFavoriteMoviesUseCase
import kotlinx.coroutines.launch


class FavoritesViewModel(
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase
): ViewModel() {
    var state by mutableStateOf(UiState())
        private set

    init {
        viewModelScope.launch {
            state = UiState(loading = true)
            getFavoriteMoviesUseCase().collect{ movies ->
                state = UiState(loading = false, movies = movies)
            }
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val movies: List<Movie> = emptyList()
    )



}