package com.example.mynba.ui.gameStatus

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynba.api.models.gameStatus.Data
import com.example.mynba.api.repo.NbaRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameStatusViewModel : ViewModel() {


    private val repo: NbaRepo = NbaRepo()

    fun getGamesStatus(gameId: String): LiveData<List<Data>> {
        var tempList: List<Data> = emptyList()
        val gameStatusList: MutableLiveData<List<Data>> = MutableLiveData()

        viewModelScope.launch(Dispatchers.IO) {
            tempList = repo.getGamesStatus(gameId)
        }.invokeOnCompletion {
            viewModelScope.launch {
                gameStatusList.value = tempList
            }
        }
        return gameStatusList
    }

}