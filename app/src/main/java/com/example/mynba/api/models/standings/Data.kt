package com.example.mynba.api.models.standings

data class Data(
    val away_keys: AwayKeys,
    val home_keys: HomeKeys,
    val id: Int,
    val round: Int,
    val slug: String,
    val standings_rows: List<StandingsRow>,
    val total_keys: TotalKeys
)