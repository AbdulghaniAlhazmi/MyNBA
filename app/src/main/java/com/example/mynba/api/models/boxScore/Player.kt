package com.example.mynba.api.models.boxScore

data class Player(
    val ability: Any,
    val age: Int,
    val characteristics: Any,
    val contract_until: String,
    val date_birth_at: String,
    val flag: String,
    val has_photo: Boolean,
    val height: Double,
    val id: Int,
    val market_currency: String,
    val market_value: Any,
    val name: String,
    val name_short: String,
    val nationality_code: String,
    val photo: String,
    val position: String,
    val position_name: String,
    val positions: Any,
    val preferred_foot: Any,
    val rating: Any,
    val shirt_number: Int,
    val slug: String,
    val sport_id: Int,
    val weight: Int
)