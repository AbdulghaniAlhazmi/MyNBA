package com.example.mynba.api.models.newgames

data class League(
    val has_logo: Boolean,
    val id: Int,
    val logo: String,
    val name: String,
    val section_id: Int,
    val slug: String,
    val sport_id: Int
)