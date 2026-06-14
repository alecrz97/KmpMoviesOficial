package io.alecrz.kmpmovies.ui.screens

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import io.alecrz.kmpmovies.data.database.MoviesDao
import io.alecrz.kmpmovies.di.rememberAppDependencies
import io.alecrz.kmpmovies.ui.screens.detail.DetailScreen
import io.alecrz.kmpmovies.ui.screens.detail.DetailViewModel
import io.alecrz.kmpmovies.ui.screens.favorites.FavoritesScreen
import io.alecrz.kmpmovies.ui.screens.favorites.FavoritesViewModel
import io.alecrz.kmpmovies.ui.screens.home.HomeScreen
import io.alecrz.kmpmovies.ui.screens.home.HomeViewModel
import io.alecrz.kmpmovies.ui.screens.watchlist.WatchlistScreen
import io.alecrz.kmpmovies.ui.screens.watchlist.WatchlistViewModel


@Composable
fun Navigation(moviesDao: MoviesDao) {
    val navController = rememberNavController()
    val dependencies = rememberAppDependencies(moviesDao)



    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onMovieClick = { movie ->
                    navController.navigate("details/${movie.id}")
                },
                onFavoritesClick = { navController.navigate("favorites") },
                onWatchlistClick = { navController.navigate("watchlist") },
                vm = viewModel {
                    HomeViewModel(
                        dependencies.getPopularMoviesUseCase,
                        dependencies.loadPopularMoviesUseCase,
                        dependencies.searchMoviesUseCase
                    )
                }
            )
        }

        composable("favorites") {
            FavoritesScreen(
                vm = viewModel {
                    FavoritesViewModel(dependencies.getFavoriteMoviesUseCase)
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
                    WatchlistViewModel(dependencies.getWatchlistMoviesUseCase)
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
        ) { backStackEntry ->
            val movieId = checkNotNull(backStackEntry.arguments?.getInt("movieId"))
            DetailScreen(
                vm = viewModel {
                    DetailViewModel(
                        movieId,
                        dependencies.getMovieByIdUseCase,
                        dependencies.toggleFavoriteUseCase,
                        dependencies.toggleWatchlistUseCase
                    )
                },
                onBack = { navController.popBackStack() }
            )
        }

    }
}

