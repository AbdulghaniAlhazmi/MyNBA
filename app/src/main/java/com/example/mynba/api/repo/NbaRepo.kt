package com.example.mynba.api.repo

import android.util.Log
import com.example.mynba.api.NbaApi
import com.example.mynba.api.models.nba.Game
import com.example.mynba.api.models.nba.Standing
import com.example.mynba.api.models.news.Article
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "NbaRepo"
class NbaRepo {

    private val retrofitNba: Retrofit = Retrofit.Builder()
        .baseUrl("https://api-nba-v1.p.rapidapi.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    private val retrofitNews: Retrofit = Retrofit.Builder()
        .baseUrl("https://newsapi.org")
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    private val nbaApi: NbaApi = retrofitNba.create(NbaApi::class.java)
    private val newsApi : NbaApi = retrofitNews.create(NbaApi::class.java)

    suspend fun getStandingsEast(): List<Standing> {
        var standingList: List<Standing> = emptyList()
        val response = nbaApi.getStandingsEast().awaitResponse()

        if (response.isSuccessful) {
            standingList = response.body()?.api?.standings ?: emptyList()
        } else {
            Log.d(TAG, "the error is {${response.errorBody()}")
        }
        return standingList
    }

    suspend fun getStandingsWest(): List<Standing> {
        var standingList: List<Standing> = emptyList()
        val response = nbaApi.getStandingsWest().awaitResponse()

        if (response.isSuccessful) {
            standingList = response.body()?.api?.standings ?: emptyList()
        } else {
            Log.d(TAG, "the error is {${response.errorBody()}")
        }
        return standingList
    }

    suspend fun getNews(): List<Article> {
        var newsList: List<Article> = emptyList()
        val response = newsApi.getNews().awaitResponse()

        if (response.isSuccessful) {
            newsList = response.body()?.articles ?: emptyList()
        } else {
            Log.d(TAG, "the error is {${response.errorBody()}")
        }
        return newsList
    }

    suspend fun getGames(query : String) : List<Game>{
        var gamesList : List<Game> = emptyList()
        val response = nbaApi.getGames(query).awaitResponse()

        if (response.isSuccessful){
            gamesList = response.body()?.api?.games!!
       }
        return gamesList
    }


}