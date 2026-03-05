package com.example.testing8.data.remote

import com.example.testing8.data.model.MoviesResponse
import retrofit2.http.GET

interface ApiService {
    @GET("movies/paginated")
    suspend fun getMovies() : MoviesResponse
}