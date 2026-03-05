package com.example.testing8.data.model

data class Data(
    val adult: Int,
    val backdrop_path: String,
    val casts: List<Cast>,
    val created_at: Any,
    val id: String,
    val movie_id: Int,
    val original_language: String,
    val original_title: String?,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val updated_at: Any,
    val vote_average: Double,
    val vote_count: Int
)