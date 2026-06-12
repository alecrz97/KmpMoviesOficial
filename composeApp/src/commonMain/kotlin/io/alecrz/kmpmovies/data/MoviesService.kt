package io.alecrz.kmpmovies.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class MoviesService(private val client: HttpClient) {

    suspend fun fetchPopularMovies(page: Int = 1): RemoteResult {
        return client
            .get("/3/discover/movie") {
                parameter("sort_by", "popularity.desc")
                parameter("page", page)
            }
            .body<RemoteResult>()
    }

    suspend fun fetchMovieById(id: Int): RemoteMovie {
        return client
            .get("/3/movie/$id")
            .body<RemoteMovie>()
    }

    suspend fun searchMovies(query: String, page: Int = 1): RemoteResult {
        return client
            .get("/3/search/movie") {
                parameter("query", query)
                parameter("page", page)
            }
            .body<RemoteResult>()

    }
}