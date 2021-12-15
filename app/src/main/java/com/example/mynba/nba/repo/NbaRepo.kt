package com.example.mynba.nba.repo

import android.util.Log
import com.example.mynba.nba.api.NbaApi
import com.example.mynba.nba.models.Standing
import com.example.mynba.nba.models.news.Article
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "NbaRepo"
class NbaRepo {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api-nba-v1.p.rapidapi.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    private val retrofit2: Retrofit = Retrofit.Builder()
        .baseUrl("https://newsapi.org")
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    private val nbaApi: NbaApi = retrofit.create(NbaApi::class.java)
    private val newsApi : NbaApi = retrofit2.create(NbaApi::class.java)

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


}