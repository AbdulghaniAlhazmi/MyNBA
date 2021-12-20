package com.example.mynba.ui.standings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynba.api.models.standings.Data
import com.example.mynba.api.models.standings.StandingsRow
import com.example.mynba.api.repo.NbaRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StandingsViewModel : ViewModel() {

    private val repo : NbaRepo = NbaRepo()


    fun getStandings(conference : String):LiveData<List<StandingsRow>>{
        var tempList : List<Data> = emptyList()
        val standingsLiveData : MutableLiveData<List<StandingsRow>> = MutableLiveData()

        viewModelScope.launch(Dispatchers.IO){
            tempList = repo.getStandings()
        }.invokeOnCompletion {
            viewModelScope.launch {
                standingsLiveData.value = tempList.filter { it.slug == conference }
                    .flatMap { it.standings_rows }
                    .sortedBy { it.position }
            }
        }
        return standingsLiveData
    }
}