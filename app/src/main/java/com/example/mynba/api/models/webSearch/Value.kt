package com.example.mynba.api.models.webSearch

data class Value(
    val body: String,
    val datePublished: String,
    val description: String,
    val id: String,
    val image: Image,
    val isSafe: Boolean,
    val keywords: String,
    val language: String,
    val snippet: String,
    val title: String,
    val url: String
)