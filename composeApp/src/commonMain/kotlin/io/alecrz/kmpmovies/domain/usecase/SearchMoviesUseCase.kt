package io.alecrz.kmpmovies.domain.usecase

import io.alecrz.kmpmovies.domain.model.Movie
import io.alecrz.kmpmovies.domain.repository.MoviesRepository

class SearchMoviesUseCase(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(query: String, page: Int = 1): List<Movie> {
        return repository.searchMovies(query = query, page = page)
    }
}