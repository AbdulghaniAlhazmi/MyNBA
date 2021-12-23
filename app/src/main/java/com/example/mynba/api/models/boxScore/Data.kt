package com.example.mynba.api.models.boxScore

data class Data(
    val event_id: Int,
    val id: Int,
    val is_confirmed: Boolean,
    val lineup_players: List<LineupPlayer>,
    val missing_players: List<MissingPlayer>,
    val team: Team,
    val team_id: Int,
)