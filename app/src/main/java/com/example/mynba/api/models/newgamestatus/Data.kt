package com.example.mynba.api.models.newgamestatus

data class Data(
    val away: String,
    val compare_code: Int,
    val event_id: Int,
    val group: String,
    val home: String,
    val id: Int,
    val name: String,
    val period: String
)