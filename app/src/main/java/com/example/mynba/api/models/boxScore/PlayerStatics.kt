package com.example.mynba.api.models.boxScore

data class PlayerStatics(
    val seconds_played : Int,
    val points : Int,
    val two_points_made : String,
    val two_point_attempts : String,
    val three_points_made : String,
    val three_point_attempts : String,
    val free_throws_made : String,
    val free_throw_attempts : String,
    val field_goals_made : String,
    val field_goal_attempts : String,
    val rebounds : String,
    val defensive_rebounds : String,
    val offensive_rebounds : String,
    val turnovers : String,
    val blocks : String,
    val personal_fouls : String,
    val assists : String,
    val steals : String,
    val plus_minus : String,
    val field_goal_pct : String,
    val player : List<Player>

)
