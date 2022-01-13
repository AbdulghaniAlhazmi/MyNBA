package com.example.mynba.api.models.boxScore

data class Team(
    val category_id: Int,
    val country_code: String,
    val flag: String,
    val foundation: Any,
    val has_logo: Boolean,
    val has_sub: Boolean,
    val id: Int,
    val logo: String,
    val name: String,
    val name_code: String,
    val name_full: String,
    val name_short: String,
)