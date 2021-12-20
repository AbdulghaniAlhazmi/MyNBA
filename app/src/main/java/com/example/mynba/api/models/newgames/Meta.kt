package com.example.mynba.api.models.newgames

data class Meta(
    val current_page: String,
    val from: Int,
    val last_page: Int,
    val per_page: Int,
    val to: Int,
    val total: Int
)