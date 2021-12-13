package com.example.mynba.ui.standings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynba.nba.models.Standing
import com.example.mynba.nba.models.Team
import com.example.mynba.nba.repo.NbaRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StandingsViewModel : ViewModel() {

    private val repo : NbaRepo = NbaRepo()

    fun getStandings():LiveData<List<Standing>>{
        var tempList : List<Standing> = emptyList()
        val standingsLiveData : MutableLiveData<List<Standing>> = MutableLiveData()

        viewModelScope.launch(Dispatchers.IO){
            tempList = repo.getStandings()
        }.invokeOnCompletion {
            viewModelScope.launch {
                standingsLiveData.value = tempList
            }
        }
        return standingsLiveData
    }

}