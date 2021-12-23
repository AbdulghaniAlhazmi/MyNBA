package com.example.mynba.api.models.test

data class MissingPlayer(
    val id: Int,
    val lineup_id: Int,
    val player: Player,
    val player_id: Int,
    val reason: Int,
    val type: String
)