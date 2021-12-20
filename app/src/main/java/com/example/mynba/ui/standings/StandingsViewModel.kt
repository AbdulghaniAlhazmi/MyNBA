package com.example.mynba.ui.standings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynba.api.models.nba.Standing
import com.example.mynba.api.models.newStandings.Data
import com.example.mynba.api.models.newStandings.StandingsRow
import com.example.mynba.api.repo.NbaRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StandingsViewModel : ViewModel() {

    private val repo : NbaRepo = NbaRepo()


    fun getStandingsEast():LiveData<List<Data>>{
        var tempList : List<Data> = emptyList()
        val standingsLiveData : MutableLiveData<List<Data>> = MutableLiveData()

        viewModelScope.launch(Dispatchers.IO){
            tempList = repo.getStandings()
        }.invokeOnCompletion {
            viewModelScope.launch {
                standingsLiveData.value = tempList.filter { it.slug == "Eastern Conference" }
            }
        }
        return standingsLiveData
    }



//    fun getStandingsWest():LiveData<List<Standing>>{
//        var tempList : List<Standing> = emptyList()
//        val standingsLiveData : MutableLiveData<List<Standing>> = MutableLiveData()
//
//        viewModelScope.launch(Dispatchers.IO){
//            tempList = repo.getStandingsWest()
//        }.invokeOnCompletion {
//            viewModelScope.launch {
//                standingsLiveData.value = tempList.sortedBy { it.conference.rank }
//            }
//        }
//        return standingsLiveData
//    }



}