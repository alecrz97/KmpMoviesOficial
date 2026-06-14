package io.alecrz.kmpmovies.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import io.alecrz.kmpmovies.BuildConfig
import io.alecrz.kmpmovies.data.MoviesService
import io.alecrz.kmpmovies.data.database.MoviesDao
import io.alecrz.kmpmovies.data.repository.MoviesRepositoryImpl
import io.alecrz.kmpmovies.domain.repository.MoviesRepository
import io.alecrz.kmpmovies.domain.usecase.GetFavoriteMoviesUseCase
import io.alecrz.kmpmovies.domain.usecase.GetMovieByIdUseCase
import io.alecrz.kmpmovies.domain.usecase.GetPopularMoviesUseCase
import io.alecrz.kmpmovies.domain.usecase.GetWatchlistMoviesUseCase
import io.alecrz.kmpmovies.domain.usecase.LoadPopularMoviesUseCase
import io.alecrz.kmpmovies.domain.usecase.SearchMoviesUseCase
import io.alecrz.kmpmovies.domain.usecase.ToggleFavoriteUseCase
import io.alecrz.kmpmovies.domain.usecase.ToggleWatchlistUseCase
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class AppDependencies (
    val moviesRepository: MoviesRepository,
    val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    val getMovieByIdUseCase: GetMovieByIdUseCase,
    val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    val getWatchlistMoviesUseCase: GetWatchlistMoviesUseCase,
    val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    val toggleWatchlistUseCase: ToggleWatchlistUseCase,
    val loadPopularMoviesUseCase: LoadPopularMoviesUseCase,
    val searchMoviesUseCase: SearchMoviesUseCase,
    )

@Composable
fun rememberAppDependencies(moviesDao: MoviesDao): AppDependencies{
    return remember (moviesDao){
        val httpClient = HttpClient {
            install(ContentNegotiation){
                json(
                    Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(DefaultRequest){
                url{
                    protocol= URLProtocol.HTTPS
                    host= "api.themoviedb.org"
                    parameters.append("api_key", BuildConfig.API_KEY)
                }
            }
        }

        val moviesService = MoviesService(httpClient)
        val moviesRepository = MoviesRepositoryImpl(moviesService, moviesDao)

        AppDependencies(
            moviesRepository = moviesRepository,
            getPopularMoviesUseCase = GetPopularMoviesUseCase(moviesRepository),
            getMovieByIdUseCase = GetMovieByIdUseCase(moviesRepository),
            getFavoriteMoviesUseCase = GetFavoriteMoviesUseCase(moviesRepository),
            getWatchlistMoviesUseCase = GetWatchlistMoviesUseCase(moviesRepository),
            toggleFavoriteUseCase = ToggleFavoriteUseCase(moviesRepository),
            toggleWatchlistUseCase = ToggleWatchlistUseCase(moviesRepository),
            loadPopularMoviesUseCase = LoadPopularMoviesUseCase(moviesRepository),
            searchMoviesUseCase = SearchMoviesUseCase(moviesRepository),
        )
    }
}