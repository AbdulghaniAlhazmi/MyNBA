package com.example.mynba.api


import com.example.mynba.api.models.gameStatus.StatusResponse
import com.example.mynba.api.models.games.GamesResponse
import com.example.mynba.api.models.news.NewsResponse
import com.example.mynba.api.models.standings.StandingsResponse
import com.example.mynba.api.models.boxScore.ScoreResponse
import com.example.mynba.api.models.gameMedia.MediaResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface NbaApi {


    @Headers(
        "x-rapidapi-host:sportscore1.p.rapidapi.com",
        "x-rapidapi-key:9d71b15abfmsh023706e8cf3e55cp17600ajsn862e0a5d003f"
    )
    @GET("/seasons/10345/standings-tables")
    fun getStandings(): Call<StandingsResponse>


    @Headers(
        "x-rapidapi-host:sportscore1.p.rapidapi.com",
        "x-rapidapi-key:9d71b15abfmsh023706e8cf3e55cp17600ajsn862e0a5d003f"
    )
    @GET("/sports/3/events/date/{date}")
    fun getGames(@Path("date") date: String): Call<GamesResponse>


    @Headers(
        "x-rapidapi-host:sportscore1.p.rapidapi.com",
        "x-rapidapi-key:9d71b15abfmsh023706e8cf3e55cp17600ajsn862e0a5d003f"
    )
    @GET("events/{gameId}/statistics")
    fun getGameStatus(@Path("gameId") gameId: String): Call<StatusResponse>


    @Headers(
        "x-rapidapi-host:sportscore1.p.rapidapi.com",
        "x-rapidapi-key:9d71b15abfmsh023706e8cf3e55cp17600ajsn862e0a5d003f"
    )
    @GET("events/{gameId}/lineups")
    fun getGameBoxScore(@Path("gameId") gameId: String): Call<ScoreResponse>

    @Headers(
        "x-rapidapi-host:sportscore1.p.rapidapi.com",
        "x-rapidapi-key:9d71b15abfmsh023706e8cf3e55cp17600ajsn862e0a5d003f"
    )
    @GET("/events/{gameId}/medias")
    fun getGameMedia(@Path("gameId") gameId: String): Call<MediaResponse>


    @Headers(
        "x-rapidapi-host:contextualwebsearch-websearch-v1.p.rapidapi.com",
        "x-rapidapi-key:9d71b15abfmsh023706e8cf3e55cp17600ajsn862e0a5d003f"
    )
    @GET("/api/search/NewsSearchAPI?q=NBA&pageNumber=1&pageSize=20&autoCorrect=true&safeSearch=true&withThumbnails=true")
    fun getNews(): Call<NewsResponse>

}