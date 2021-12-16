package com.example.mynba.api


import com.example.mynba.api.models.nba.GamesResponse
import com.example.mynba.api.models.nba.NbaResponse
import com.example.mynba.api.models.news.ArticleResponse
import retrofit2.Call
import retrofit2.http.GET

interface NbaApi {

    @GET("standings/standard/2021/conference/East?rapidapi-key=9d71b15abfmsh023706e8cf3e55cp17600ajsn862e0a5d003f")
    fun getStandingsEast(): Call<NbaResponse>

    @GET("standings/standard/2021/conference/West?rapidapi-key=9d71b15abfmsh023706e8cf3e55cp17600ajsn862e0a5d003f")
    fun getStandingsWest(): Call<NbaResponse>

    @GET("/games/date/2021-12-16?rapidapi-key=9d71b15abfmsh023706e8cf3e55cp17600ajsn862e0a5d003f")
    fun getGames(): Call<GamesResponse>


    @GET("/v2/everything?qInTitle=NBA&apiKey=cbd062c6abf743609cc2ad5bd95d8708")
    fun getNews(): Call<ArticleResponse>


}