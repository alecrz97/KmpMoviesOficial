package io.alecrz.kmpmovies.domain.repository

import io.alecrz.kmpmovies.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun getPopularMovies(): Flow<List<Movie>>
    fun getMovieById(id:Int): Flow<Movie?>
    fun getFavoriteMovies(): Flow<List<Movie>>
    fun getWatchlistMovies(): Flow<List<Movie>>
    suspend fun loadPopularMovies(page: Int)
    suspend fun searchMovies(query: String, page: Int = 1): List<Movie>
    suspend fun toggleFavorite(id: Int, isFavorite: Boolean)
    suspend fun toggleWatchlist(id: Int, isWatchlist: Boolean)

}