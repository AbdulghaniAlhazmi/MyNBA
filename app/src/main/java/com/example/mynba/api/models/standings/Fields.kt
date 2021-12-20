package com.example.mynba.api.models.standings

data class Fields(
    val draws_total: String,
    val games_behind_total: String,
    val goals_total: String,
    val losses_total: String,
    val matches_total: String,
    val net_run_rate_total: String,
    val no_result_total: String,
    val one_point_total: String,
    val overtime_and_penalty_wins_total: String,
    val overtime_losses_total: String,
    val overtime_wins_total: String,
    val pct_goal_total: String,
    val penalty_losses_total: String,
    val penalty_wins_total: String,
    val percentage_total: String,
    val points_total: String,
    val score_diff_formatted_total: String,
    val streak_total: String,
    val three_points_total: String,
    val two_points_total: String,
    val wins_losses_total: String,
    val wins_total: String
)