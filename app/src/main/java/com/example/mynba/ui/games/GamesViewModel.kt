package com.example.mynba.ui.games

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import com.example.mynba.api.models.nba.Game
import com.example.mynba.api.repo.NbaRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GamesViewModel : ViewModel() {

    private val repo : NbaRepo = NbaRepo()



    fun getGames(query: String) : LiveData<List<Game>>{
        var tempList : List<Game> = emptyList()
        val gamesList : MutableLiveData<List<Game>> = MutableLiveData()

        viewModelScope.launch(Dispatchers.IO){
            tempList = repo.getGames(query)
        }.invokeOnCompletion {
            viewModelScope.launch{
                gamesList.value = tempList
            }
        }
        return gamesList
    }

}
