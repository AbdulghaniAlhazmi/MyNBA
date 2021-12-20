package com.example.mynba.api.models.games

data class Challenge(
    val id: Int,
    val league_id: Int,
    val name: String,
    val order: Int,
    val priority: Int,
    val slug: String,
    val sport_id: Int
)