package com.example.mynba.api.models.nba

data class Standing(
    val conference: Conference,
    val division: Division,
    val league: String,
    val gamesBehind: String,
    val winPercentage: String,
    val loss: String,
    val teamId: String,
    val logo : Team,
    val win: String,
)