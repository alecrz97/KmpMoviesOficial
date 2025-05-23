package io.alecrz.kmpmovies.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.alecrz.kmpmovies.data.Movie
import io.alecrz.kmpmovies.data.MoviesRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: MoviesRepository
) : ViewModel() {

    var state by mutableStateOf(UiState())
        private set

    init {
        viewModelScope.launch {
            state = UiState(loading = true)
            repository.movies.collect{
                if(it.isNotEmpty()){
                    state = UiState(loading = false, movies = it)
                }
            }
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val movies: List<Movie> = emptyList()
    )
}

