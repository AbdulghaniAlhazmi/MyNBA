package com.example.mynba.api.models.boxScore

data class LineupPlayer(
    val id : String,
    val player_id : String,
    val lineup_id : String,
    val shirt_number : String,
    val substitute : Boolean,
    val position_key : String,
    val is_captain : Boolean,
    val player_statistics : PlayerStatics,
    val player : Player
)
