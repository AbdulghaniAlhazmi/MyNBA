package com.example.mynba.api


import com.example.mynba.api.models.gameStatus.ApiStatusResponse
import com.example.mynba.api.models.nba.NbaResponse
import com.example.mynba.api.models.newStandings.StandingsResponse
import com.example.mynba.api.models.newgames.GamesResponse
import com.example.mynba.api.models.newgamestatus.StatusResponse
import com.example.mynba.api.models.news.ArticleResponse
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
    fun getGames(@Path("date") query: String) : Call<GamesResponse>


    @Headers(
        "x-rapidapi-host:sportscore1.p.rapidapi.com",
        "x-rapidapi-key:9d71b15abfmsh023706e8cf3e55cp17600ajsn862e0a5d003f"
    )
    @GET("events/{gameId}/statistics")
    fun getGameStatus(@Path("gameId") gameId: String) : Call<StatusResponse>






    @GET("/v2/everything?qInTitle=NBA&from=2021-12-18&apiKey=cbd062c6abf743609cc2ad5bd95d8708")
    fun getNews(): Call<ArticleResponse>


//    @GET("gameDetails/{gameId}?rapidapi-key=9d71b15abfmsh023706e8cf3e55cp17600ajsn862e0a5d003f")
//    fun getGamesStatus(@Path("gameId") gameId: String) : Call<ApiStatusResponse>

}