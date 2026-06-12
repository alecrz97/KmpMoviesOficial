package io.alecrz.kmpmovies.domain.usecase

import io.alecrz.kmpmovies.domain.repository.MoviesRepository

class ToggleWatchlistUseCase(private val repository: MoviesRepository) {
    suspend operator fun invoke(id: Int, isWatchlist: Boolean) {
        repository.toggleWatchlist(id, isWatchlist)
    }
    }
