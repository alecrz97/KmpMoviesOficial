package io.alecrz.kmpmovies.data

import io.alecrz.kmpmovies.data.local.MovieEntity
import io.alecrz.kmpmovies.domain.model.Movie

fun MovieEntity.toDomain(): Movie = Movie(
    id=id,
    title = title,
    overview = overview,
    releaseDate = releaseDate,
    poster = poster,
    backdrop = backdrop,
    originalTitle = originalTitle,
    originalLanguage = originalLanguage,
    popularity = popularity,
    voteAverage = voteAverage,
    isFavorite = isFavorite,
    isWatchlist = isWatchlist

)

fun RemoteMovie.toEntity(): MovieEntity = MovieEntity(
    id = id,
    title = title,
    overview = overview,
    releaseDate = releaseDate,
    poster = posterPath?.let{ "https://image.tmdb.org/t/p/w185/$it"} ?: "",
    backdrop = backdropPath?.let { "https://image.tmdb.org/t/p/w780/$it" },
    originalTitle = originalTitle,
    originalLanguage = originalLanguage,
    popularity = popularity,
    voteAverage = voteAverage,
    isFavorite = false,
    isWatchlist = false
)
