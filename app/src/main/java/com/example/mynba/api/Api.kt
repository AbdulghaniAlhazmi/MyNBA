package com.example.mynba.api


import com.example.mynba.api.models.gameStatus.ApiStatusResponse
import com.example.mynba.api.models.nba.GamesResponse
import com.example.mynba.api.models.nba.NbaResponse
import com.example.mynba.api.models.news.ArticleResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface NbaApi {


    @GET("standings/standard/2021/conference/East?rapidapi-key=9d71b15abfmsh023706e8cf3e55cp17600ajsn862e0a5d003f")
    fun getStandingsEast(): Call<NbaResponse>

    @GET("standings/standard/2021/conference/West?rapidapi-key=9d71b15abfmsh023706e8cf3e55cp17600ajsn862e0a5d003f")
    fun getStandingsWest(): Call<NbaResponse>

    @GET("/games/date/{date}?rapidapi-key=9d71b15abfmsh023706e8cf3e55cp17600ajsn862e0a5d003f")
    fun getGames(@Path("date") query: String) : Call<GamesResponse>


    @GET("/v2/everything?qInTitle=NBA&from=2021-12-18&apiKey=cbd062c6abf743609cc2ad5bd95d8708")
    fun getNews(): Call<ArticleResponse>


    @GET("gameDetails/{gameId}?rapidapi-key=9d71b15abfmsh023706e8cf3e55cp17600ajsn862e0a5d003f")
    fun getGamesStatus(@Path("gameId") gameId: String) : Call<ApiStatusResponse>

}