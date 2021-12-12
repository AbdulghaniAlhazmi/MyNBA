package com.example.mynba.nba.api


import com.example.mynba.nba.models.NbaResponse
import retrofit2.Call
import retrofit2.http.GET

interface NbaApi {

    @GET("/standings/standard/2021/?rapidapi-key=9d71b15abfmsh023706e8cf3e55cp17600ajsn862e0a5d003f")
    fun getStandings(): Call<NbaResponse>


}