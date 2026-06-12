package io.alecrz.kmpmovies.domain.usecase

import io.alecrz.kmpmovies.domain.repository.MoviesRepository

class LoadPopularMoviesUseCase(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(page: Int) {
        repository.loadPopularMovies(page)
    }
}
