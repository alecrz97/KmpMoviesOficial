package io.alecrz.kmpmovies.domain.usecase

import io.alecrz.kmpmovies.domain.model.Movie
import io.alecrz.kmpmovies.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow

class GetPopularMoviesUseCase(private val repository: MoviesRepository) {
    operator fun invoke(): Flow<List<Movie>> {
        return repository.getPopularMovies()
    }
}