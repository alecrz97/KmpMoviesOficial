package io.alecrz.kmpmovies.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.alecrz.kmpmovies.domain.model.Movie
import io.alecrz.kmpmovies.domain.usecase.GetPopularMoviesUseCase
import io.alecrz.kmpmovies.domain.usecase.LoadPopularMoviesUseCase
import io.alecrz.kmpmovies.domain.usecase.SearchMoviesUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class HomeViewModel(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val loadPopularMoviesUseCase: LoadPopularMoviesUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase

) : ViewModel() {

    private var allMovies: List<Movie> = emptyList()
    private var currentPage = 1
    private var isLoadingMore = false
    private var searchJob: Job? = null
    private val searchDebounceMillis = 500L

    var state by mutableStateOf(UiState())
        private set

    init {
        observePupularMovies()
    }

    private fun observePupularMovies(){
        viewModelScope.launch {
            state = state.copy(loading = true, error = null)
            try {
                getPopularMoviesUseCase().collect { movies ->
                    allMovies = movies
                    if (state.query.isBlank()){
                        state = state.copy(
                            loading = false,
                            error = null,
                            movies = movies
                        )
                    }
                }
            } catch (e: Exception) {
                state =
                    state.copy(
                        loading = false,
                        error = "Ocurrio un error al cargar las peliculas"
                    )
            }
        }
    }


    fun onQueryChange(query: String) {
        state = state.copy(
            query = query,
            error = null
        )

        if(query.isBlank()){
            searchJob?.cancel()
            state = state.copy(
                loading = false,
                movies = allMovies,
                error = null)
            return
        }
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            state = state.copy(loading = true)

            try {
                delay(searchDebounceMillis)

                val results = searchMoviesUseCase(query)
                state = state.copy(
                    loading = false,
                    movies = results,
                    error = null
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                state = state.copy(
                    loading= false,
                    movies = emptyList(),
                    error = "Ocurrio un error al buscar peliculas"
                )
            }
        }
    }

    fun loadMoreMovies(){
        if (state.loading || isLoadingMore || state.query.isNotBlank()) return

        isLoadingMore = true
        state = state.copy(isLoadingMore = true)

        viewModelScope.launch {
            try {
                val nextPage = currentPage + 1
                loadPopularMoviesUseCase(nextPage)
                currentPage = nextPage
                state = state.copy(isLoadingMore = false, error = null)
            } catch (e: Exception) {
                state = state.copy(
                    isLoadingMore = false,
                    error = "No se pudieron cargar mas peliculas"
                )
            } finally {
                isLoadingMore=false
            }
        }
    }



    data class UiState(
        val loading: Boolean = false,
        val query: String = "",
        val movies: List<Movie> = emptyList(),
        val error: String? = null,
        val isLoadingMore: Boolean = false
    )
}

