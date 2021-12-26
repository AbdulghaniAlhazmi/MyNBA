package com.example.mynba.ui.boxScore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynba.api.models.boxScore.Data
import com.example.mynba.api.models.boxScore.LineupPlayer
import com.example.mynba.api.repo.NbaRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BoxScoreViewModel : ViewModel() {

    private val repo : NbaRepo = NbaRepo()

    fun getGameBoxScore(gameId : String,teamId : Int) : LiveData<List<LineupPlayer>>{
        var tempList : List<Data> = emptyList()
        val gameBoxScoreList : MutableLiveData<List<LineupPlayer>> = MutableLiveData()

        viewModelScope.launch(Dispatchers.IO){
            tempList = repo.getGameBoxScore(gameId)
        }.invokeOnCompletion {
            viewModelScope.launch {
                gameBoxScoreList.value = tempList.filter { it.is_confirmed }
                    .filter { it.team_id == teamId }
                    .flatMap { it.lineup_players }
                    .filter { !it.substitute }
                    .sortedByDescending { it.player_statistics.seconds_played }
            }
        }
        return gameBoxScoreList
    }

}