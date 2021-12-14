package com.example.mynba.ui.standings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynba.nba.models.Standing
import com.example.mynba.nba.repo.NbaRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StandingsViewModel : ViewModel() {

    private val repo : NbaRepo = NbaRepo()


    fun getStandingsEast():LiveData<List<Standing>>{
        var tempList : List<Standing> = emptyList()
        val standingsLiveData : MutableLiveData<List<Standing>> = MutableLiveData()

        viewModelScope.launch(Dispatchers.IO){
            tempList = repo.getStandingsEast()
        }.invokeOnCompletion {
            viewModelScope.launch {
                standingsLiveData.value = tempList.sortedBy { it.conference.rank }
            }
        }
        return standingsLiveData
    }

    fun getStandingsWest():LiveData<List<Standing>>{
        var tempList : List<Standing> = emptyList()
        val standingsLiveData : MutableLiveData<List<Standing>> = MutableLiveData()

        viewModelScope.launch(Dispatchers.IO){
            tempList = repo.getStandingsWest()
        }.invokeOnCompletion {
            viewModelScope.launch {
                standingsLiveData.value = tempList.sortedBy { it.conference.rank }
            }
        }
        return standingsLiveData
    }



}