package com.example.mynba.nba.api


import com.example.mynba.nba.models.NbaResponse
import com.example.mynba.nba.models.news.ArticleResponse
import retrofit2.Call
import retrofit2.http.GET

interface NbaApi {

    @GET("standings/standard/2021/conference/East?rapidapi-key=9d71b15abfmsh023706e8cf3e55cp17600ajsn862e0a5d003f")
    fun getStandingsEast(): Call<NbaResponse>

    @GET("standings/standard/2021/conference/West?rapidapi-key=9d71b15abfmsh023706e8cf3e55cp17600ajsn862e0a5d003f")
    fun getStandingsWest(): Call<NbaResponse>

    @GET("/v2/everything?qInTitle=NBA&apiKey=cbd062c6abf743609cc2ad5bd95d8708")
    fun getNews(): Call<ArticleResponse>


}