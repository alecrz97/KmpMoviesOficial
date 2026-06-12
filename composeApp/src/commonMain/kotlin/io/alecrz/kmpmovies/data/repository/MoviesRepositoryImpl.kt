package io.alecrz.kmpmovies.data.repository

import io.alecrz.kmpmovies.data.MoviesService
import io.alecrz.kmpmovies.data.database.MoviesDao
import io.alecrz.kmpmovies.data.toDomain
import io.alecrz.kmpmovies.data.toEntity
import io.alecrz.kmpmovies.domain.model.Movie
import io.alecrz.kmpmovies.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class MoviesRepositoryImpl(
    private val moviesService: MoviesService,
    private val moviesDao: MoviesDao
) : MoviesRepository {

    override fun getPopularMovies(): Flow<List<Movie>> =
        moviesDao.fetchPopularMovies()
            .onEach { movieEntities ->
                if (movieEntities.isEmpty()) {
                    val popularMovies = moviesService
                        .fetchPopularMovies(page = 1)
                        .results
                        .map { remoteMovie -> remoteMovie.toEntity() }

                    moviesDao.save(popularMovies)
                }
            }
            .map { movieEntities ->
                movieEntities.map { movieEntity -> movieEntity.toDomain() }
            }

    override fun getMovieById(id: Int): Flow<Movie?> =
        moviesDao.fetchMovieById(id).onEach { movieEntity ->
            if (movieEntity == null) {
                val remoteMovie = moviesService.fetchMovieById(id).toEntity()
                moviesDao.save(listOf(remoteMovie))

            }
        }.map { movieEntity -> movieEntity?.toDomain() }

    override fun getFavoriteMovies(): Flow<List<Movie>> =
        moviesDao.fetchFavoriteMovies().map { movieEntities ->
            movieEntities.map { movieEntity -> movieEntity.toDomain() }
        }

    override fun getWatchlistMovies(): Flow<List<Movie>> =
        moviesDao.fetchWatchlistMovies().map { movieEntities ->
            movieEntities.map { movieEntity -> movieEntity.toDomain() }

        }

    override suspend fun toggleFavorite(id: Int, isFavorite: Boolean) {
        moviesDao.updateFavorite(id, isFavorite)
    }

    override suspend fun toggleWatchlist(id: Int, isWatchlist: Boolean) {
        moviesDao.updateWatchlist(id, isWatchlist)
    }

    override suspend fun loadPopularMovies(page: Int) {
        val popularMovies = moviesService
            .fetchPopularMovies(page)
            .results
            .map { remoteMovie -> remoteMovie.toEntity() }

        moviesDao.save(popularMovies)
    }

    override suspend fun searchMovies(query: String, page: Int): List<Movie> {
        return moviesService
            .searchMovies(query = query, page = page)
            .results
            .map { remoteMovie ->
                remoteMovie.toEntity().toDomain()
            }
    }

}
