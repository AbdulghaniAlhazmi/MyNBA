package com.example.mynba.api.models.gameStatus

data class VTeam(
    val allStar: String,
    val fullName: String,
    val leaders: List<LeaderX>,
    val logo: String,
    val nbaFranchise: String,
    val nickname: String,
    val score: ScoreX,
    val shortName: String,
    val teamId: String
)