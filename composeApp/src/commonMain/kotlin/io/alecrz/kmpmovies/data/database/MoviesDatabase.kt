package io.alecrz.kmpmovies.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import io.alecrz.kmpmovies.data.local.MovieEntity

const val DATABASE_NAME = "movies.db"

interface DB {
    fun clearAllTables()
}

@Database(entities = [MovieEntity::class], version = 2)
abstract class MoviesDatabase : RoomDatabase(), DB {
    abstract fun moviesDao(): MoviesDao

    override fun clearAllTables() {}

}