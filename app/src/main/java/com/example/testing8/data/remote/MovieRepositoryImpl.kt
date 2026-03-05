package com.example.testing8.data.remote

import com.example.testing8.data.model.MoviesResponse
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    val apiService: ApiService
) :  MovieRepository {
    override suspend fun getMovies(): UiState<MoviesResponse> {
         return try {
            val moviesResponse = apiService.getMovies()
            UiState.Success(moviesResponse)
        } catch (exception : Exception) {
            UiState.Error(exception.message.toString())
        }
    }
}
sealed class UiState<out T>{
    data class Success<T>(val data : T) : UiState<T>()
    data class Error(val error : String) : UiState<Nothing>()
    object Loading: UiState<Nothing>()
}