package com.example.mynba.nba.repo

import android.util.Log
import com.example.mynba.nba.api.NbaApi
import com.example.mynba.nba.models.Standing
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "NbaRepo"
class NbaRepo {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api-nba-v1.p.rapidapi.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val nbaApi: NbaApi = retrofit.create(NbaApi::class.java)

    suspend fun getStandings(): List<Standing> {
        var standingList: List<Standing> = emptyList()
        val response = nbaApi.getStandings().awaitResponse()

        if (response.isSuccessful) {
            standingList = response.body()?.api?.standings ?: emptyList()
        } else {
            Log.d(TAG, "the error is {${response.errorBody()}")
        }

        return standingList
    }
}