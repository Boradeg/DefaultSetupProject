package com.example.testing8.data.remote

import com.example.testing8.data.model.MoviesResponse

interface MovieRepository {
    suspend fun getMovies () : UiState<MoviesResponse>
}

