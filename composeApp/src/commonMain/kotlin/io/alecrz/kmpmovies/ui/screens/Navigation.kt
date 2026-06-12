package io.alecrz.kmpmovies.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import io.alecrz.kmpmovies.BuildConfig
import io.alecrz.kmpmovies.domain.repository.MoviesRepository
import io.alecrz.kmpmovies.data.repository.MoviesRepositoryImpl
import io.alecrz.kmpmovies.data.MoviesService
import io.alecrz.kmpmovies.data.database.MoviesDao
import io.alecrz.kmpmovies.domain.usecase.GetFavoriteMoviesUseCase
import io.alecrz.kmpmovies.domain.usecase.GetMovieByIdUseCase
import io.alecrz.kmpmovies.domain.usecase.GetPopularMoviesUseCase
import io.alecrz.kmpmovies.domain.usecase.GetWatchlistMoviesUseCase
import io.alecrz.kmpmovies.domain.usecase.LoadPopularMoviesUseCase
import io.alecrz.kmpmovies.domain.usecase.SearchMoviesUseCase
import io.alecrz.kmpmovies.domain.usecase.ToggleFavoriteUseCase
import io.alecrz.kmpmovies.domain.usecase.ToggleWatchlistUseCase
import io.alecrz.kmpmovies.ui.screens.detail.DetailScreen
import io.alecrz.kmpmovies.ui.screens.detail.DetailViewModel
import io.alecrz.kmpmovies.ui.screens.favorites.FavoritesScreen
import io.alecrz.kmpmovies.ui.screens.favorites.FavoritesViewModel
import io.alecrz.kmpmovies.ui.screens.home.HomeScreen
import io.alecrz.kmpmovies.ui.screens.home.HomeViewModel
import io.alecrz.kmpmovies.ui.screens.watchlist.WatchlistScreen
import io.alecrz.kmpmovies.ui.screens.watchlist.WatchlistViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json



@Composable
fun Navigation(moviesDao: MoviesDao) {
    val navController = rememberNavController()
    val repository = rememberMoviesRepository(moviesDao)
    val getPopularMoviesUseCase = remember(repository) { GetPopularMoviesUseCase(repository) }
    val getMovieByIdUseCase = remember(repository) { GetMovieByIdUseCase(repository) }
    val getFavoriteMoviesUseCase = remember(repository) { GetFavoriteMoviesUseCase(repository) }
    val getWatchlistMoviesUseCase = remember(repository) { GetWatchlistMoviesUseCase(repository) }
    val toggleFavoriteUseCase = remember(repository) { ToggleFavoriteUseCase(repository) }
    val toggleWatchlistUseCase = remember(repository) { ToggleWatchlistUseCase(repository) }
    val loadPopularMoviesUseCase = remember(repository) { LoadPopularMoviesUseCase(repository) }
    val searchMoviesUseCase = remember(repository) { SearchMoviesUseCase(repository) }


    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onMovieClick = { movie ->
                    navController.navigate("details/${movie.id}")
                },
                onFavoritesClick = { navController.navigate("favorites") },
                onWatchlistClick = { navController.navigate("watchlist") },
                vm = viewModel { HomeViewModel(
                    getPopularMoviesUseCase,
                    loadPopularMoviesUseCase,
                    searchMoviesUseCase) }
            )
        }

        composable("favorites") {
            FavoritesScreen(
                vm = viewModel {
                    FavoritesViewModel(getFavoriteMoviesUseCase)
                },
                onMovieClick = { movieId ->
                    navController.navigate("details/$movieId")
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable("watchlist") {
            WatchlistScreen(
                vm = viewModel {
                    WatchlistViewModel(getWatchlistMoviesUseCase)
                },
                onMovieClick = { movieId ->
                    navController.navigate("details/$movieId")
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = "details/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry->
            val movieId = checkNotNull(backStackEntry.arguments?.getInt("movieId"))
            DetailScreen(
                vm = viewModel { DetailViewModel(movieId, getMovieByIdUseCase, toggleFavoriteUseCase,toggleWatchlistUseCase) },
                onBack = { navController.popBackStack() }
            ) }



    }
}

@Composable
private fun rememberMoviesRepository(moviesDao: MoviesDao): MoviesRepository = remember{
    val client = HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
            install(DefaultRequest) {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "api.themoviedb.org"
                    parameters.append("api_key", BuildConfig.API_KEY)
                }
            }
        }

    MoviesRepositoryImpl(MoviesService(client),moviesDao)
}