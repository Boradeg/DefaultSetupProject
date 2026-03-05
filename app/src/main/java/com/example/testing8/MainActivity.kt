package com.example.testing8

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.example.testing8.data.model.MoviesResponse
import com.example.testing8.data.remote.MovieRepository
import com.example.testing8.data.remote.UiState
import com.example.testing8.ui.theme.Testing8Theme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Testing8Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    val movieViewModel: MovieViewModel = hiltViewModel()
                    val movie by movieViewModel.movie.collectAsState()
                    when (val data = movie) {
                        is UiState.Error -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                               Text(data.error)
                            }
                        }

                        UiState.Loading -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }

                        is UiState.Success -> {
                            val movieList = data.data.data
                            LazyColumn {
                                items(movieList) { movieObj ->
                                    SubcomposeAsyncImage(
                                        model = movieObj.poster_path,
                                        contentDescription = "Movie Image",
                                        loading = {
                                            CircularProgressIndicator(
                                                modifier = Modifier.size(24.dp)
                                            )
                                        },
                                        error = {
                                            Icon(
                                                imageVector = Icons.Default.Warning,
                                                contentDescription = "Error"
                                            )
                                        }
                                    )

                                    val title = movieObj.original_title ?: "Not found"
                                    Text(title)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {
    private var _movie = MutableStateFlow<UiState<MoviesResponse>>(UiState.Loading)
    val movie: StateFlow<UiState<MoviesResponse>> = _movie

    init {
        viewModelScope.launch {
            getMovie()
        }
    }

    private suspend fun getMovie() {
        _movie.value = repository.getMovies()
    }
}


