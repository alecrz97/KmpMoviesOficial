package io.alecrz.kmpmovies.domain.usecase

import io.alecrz.kmpmovies.domain.repository.MoviesRepository

class ToggleFavoriteUseCase(private val repository: MoviesRepository) {
    suspend operator fun invoke(id: Int, isFavorite: Boolean) {
        repository.toggleFavorite(id, isFavorite)
    }
}
