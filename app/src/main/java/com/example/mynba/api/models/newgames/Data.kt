package com.example.mynba.api.models.newgames

data class Data(
    val aggregated_winner_code: Any,
    val attendance: Int,
    val away_score: AwayScore?,
    val away_team: AwayTeam,
    val away_team_id: Int,
    val cards_code: Any,
    val challenge: Challenge,
    val challenge_id: Int,
    val coverage: Any,
    val cup_match_in_round: Any,
    val cup_match_order: Any,
    val default_period_count: Int,
    val event_data_change: Any,
    val first_supply: Any,
    val ground_type: Any,
    val home_score: HomeScore?,
    val home_team: HomeTeam,
    val home_team_id: Int,
    val id: Int,
    val lasted_period: String,
    val league: League,
    val league_id: Int,
    val medias_count: Int,
    val name: String,
    val periods: Periods,
    val periods_time: PeriodsTime,
    val priority: Int,
    val referee_id: Any,
    val result_only: Boolean,
    val round_number: Int,
    val season_id: Int,
    val series_count: Int,
    val slug: String,
    val sport_id: Int,
    val start_at: String,
    val status: String,
    val status_lineup: Int,
    val status_more: String,
    val time_details: Any,
    val venue_id: Int,
    val winner_code: Int
)