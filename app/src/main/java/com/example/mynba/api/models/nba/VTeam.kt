package com.example.mynba.api.models.nba

data class VTeam(
    val fullName: String,
    val logo: String,
    val nickName: String,
    val score: Score,
    val shortName: String,
    val teamId: String
)