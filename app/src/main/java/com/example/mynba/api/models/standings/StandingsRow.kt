package com.example.mynba.api.models.standings

data class StandingsRow(
    val away_fields: AwayFields,
    val away_points: Any,
    val away_position: Int,
    val details: Details,
    val fields: Fields,
    val home_fields: HomeFields,
    val home_points: Any,
    val home_position: Int,
    val id: Int,
    val points: Int,
    val position: Int,
    val team: Team
)