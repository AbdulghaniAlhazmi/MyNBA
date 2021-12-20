package com.example.mynba.api.models.gameStatus

data class Score(
    val linescore: List<String>,
    val loss: String,
    val points: String,
    val seriesLoss: String,
    val seriesWin: String,
    val win: String
)