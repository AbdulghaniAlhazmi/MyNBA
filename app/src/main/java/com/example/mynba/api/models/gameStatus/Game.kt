package com.example.mynba.api.models.gameStatus

data class Game(
    val EndOfPeriod: String,
    val arena: String,
    val city: String,
    val clock: String,
    val country: String,
    val currentPeriod: String,
    val endTimeUTC: String,
    val gameDuration: String,
    val gameId: String,
    val hTeam: HTeam,
    val halftime: String,
    val leadChanges: String,
    val league: String,
    val officials: List<Official>,
    val seasonStage: String,
    val seasonYear: String,
    val startTimeUTC: String,
    val statusGame: String,
    val statusShortGame: String,
    val timesTied: String,
    val vTeam: VTeam
)