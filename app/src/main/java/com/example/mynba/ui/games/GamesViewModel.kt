package com.example.mynba.ui.games

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynba.api.models.games.Data
import com.example.mynba.api.repo.NbaRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GamesViewModel : ViewModel() {

    private val repo: NbaRepo = NbaRepo()

    fun getGames(date: String): LiveData<List<Data>> {
        var tempList: List<Data> = emptyList()
        val gamesList: MutableLiveData<List<Data>> = MutableLiveData()

        viewModelScope.launch(Dispatchers.IO) {
            tempList = repo.getGames(date)
        }.invokeOnCompletion {
            viewModelScope.launch {
                gamesList.value = tempList.filter { it.league_id == 7422 }
            }
        }
        return gamesList
    }

}
