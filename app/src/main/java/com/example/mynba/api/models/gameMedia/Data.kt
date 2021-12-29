package com.example.mynba.api.models.gameMedia

data class Data(
    val id: Int,
    val source_id: Int,
    val source_type: String,
    val source_url: String,
    val sub_title: String,
    val thumbnail_url: String,
    val title: Title,
    val type: Int,
    val url: String
)