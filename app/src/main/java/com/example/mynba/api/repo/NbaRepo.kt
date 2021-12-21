package com.example.mynba.api.repo

import android.util.Log
import com.example.mynba.api.NbaApi
import com.example.mynba.api.models.games.Data
import com.example.mynba.api.models.news.Value
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "NbaRepo"
class NbaRepo {

    private val retrofitNba: Retrofit = Retrofit.Builder()
        .baseUrl("https://sportscore1.p.rapidapi.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    private val retrofitNews: Retrofit = Retrofit.Builder()
        .baseUrl("https://contextualwebsearch-websearch-v1.p.rapidapi.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    private val nbaApi: NbaApi = retrofitNba.create(NbaApi::class.java)
    private val newsApi : NbaApi = retrofitNews.create(NbaApi::class.java)
//


    suspend fun getStandings() : List<com.example.mynba.api.models.standings.Data>{
        var standingList : List<com.example.mynba.api.models.standings.Data> = emptyList()
        val response = nbaApi.getStandings().awaitResponse()

        if (response.isSuccessful){
            standingList = response.body()?.data ?: emptyList()
        }else{
            Log.d(TAG, "the error is {${response.errorBody()}")
        }
        return standingList
    }


    suspend fun getNews(): List<Value> {
        var newsList: List<Value> = emptyList()
        val response = newsApi.getNews().awaitResponse()

        if (response.isSuccessful) {
            newsList = response.body()?.value ?: emptyList()
        } else {
            Log.d(TAG, "the error is {${response.errorBody()}")
        }
        return newsList.sortedBy { it.id }
    }

    suspend fun getGames(date : String) : List<Data>{
        var gamesList : List<Data> = emptyList()
        val response = nbaApi.getGames(date).awaitResponse()

        if (response.isSuccessful){
            gamesList = response.body()?.data ?: emptyList()
       }
        return gamesList
    }

    suspend fun getGamesStatus(gameId : String) : List<com.example.mynba.api.models.gameStatus.Data>{
       var gameStatusList : List<com.example.mynba.api.models.gameStatus.Data> = emptyList()
        val response = nbaApi.getGameStatus(gameId).awaitResponse()

        if (response.isSuccessful){
            gameStatusList = response.body()?.data ?: emptyList()
        }

        return gameStatusList
    }


}