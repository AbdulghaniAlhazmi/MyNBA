package com.example.mynba.nba.models

data class Team(
    val allStar: String,
    val city: String,
    val fullName: String,
    val leagues: Leagues,
    val logo: String,
    val nbaFranchise: Int,
    val nickname: String,
    val shortName: String,
    val teamId: String
)