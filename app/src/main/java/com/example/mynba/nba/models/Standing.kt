package com.example.mynba.nba.models

data class Standing(
    val conference: Conference,
    val division: Division,
    val league: String,
    val loss: String,
    val teamId: String,
    val win: String,
)