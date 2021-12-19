package com.example.mynba.api.models.gameStatus

data class ApiX(
    val filters: List<String>,
    val game: List<Game>,
    val message: String,
    val results: Int,
    val status: Int
)