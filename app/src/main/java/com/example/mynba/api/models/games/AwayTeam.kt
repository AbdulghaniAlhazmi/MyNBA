package com.example.mynba.api.models.games

data class AwayTeam(
    val category_id: Int,
    val has_logo: Boolean,
    val has_sub: Boolean,
    val id: Int,
    val is_nationality: Boolean,
    val logo: String,
    val manager_id: Int,
    val name: String,
    val name_code: String,
    val name_full: String,
    val name_short: String,
    val slug: String,
    val sport_id: Int,
    val venue_id: Int
)