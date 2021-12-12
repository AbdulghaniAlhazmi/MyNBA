package com.example.mynba.nba.models

data class Standing(
    val conference: Conference,
    val division: Division,
    val gamesBehind: String,
    val lastTenLoss: String,
    val lastTenWin: String,
    val league: String,
    val loss: String,
    val lossPercentage: String,
    val seasonYear: String,
    val streak: String,
    val teamId: String,
    val tieBreakerPoints: String,
    val win: String,
    val winPercentage: String,
    val winStreak: String
)