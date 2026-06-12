package io.alecrz.kmpmovies.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.alecrz.kmpmovies.data.local.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Query("SELECT * FROM movies")
    fun fetchPopularMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE id = :id")
    fun fetchMovieById(id: Int): Flow<MovieEntity?>

    @Query("SELECT * FROM movies WHERE isFavorite = 1")
    fun fetchFavoriteMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE isWatchlist = 1")
    fun fetchWatchlistMovies(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(movie: List<MovieEntity>)

    @Query("UPDATE movies SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavorite(id: Int, isFavorite: Boolean)

    @Query("UPDATE movies SET isWatchlist = :isWatchlist WHERE id = :id")
    suspend fun updateWatchlist(id: Int, isWatchlist: Boolean)



}