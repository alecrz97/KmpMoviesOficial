package io.alecrz.kmpmovies.domain.usecase

import io.alecrz.kmpmovies.domain.model.Movie
import io.alecrz.kmpmovies.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow

class GetMovieByIdUseCase(private val repository: MoviesRepository) {
    operator fun invoke(id: Int): Flow<Movie?> {
        return repository.getMovieById(id)
    }
}
