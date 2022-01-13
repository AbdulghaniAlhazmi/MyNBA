package com.example.mynba.api.models.games

data class HomeTeam(
    val category_id: Int,
    val flag: String,
    val gender: String,
    val has_logo: Boolean,
    val has_sub: Boolean,
    val id: Int,
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