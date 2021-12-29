package com.example.mynba.ui.gameMedia

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynba.api.models.gameMedia.Data
import com.example.mynba.api.repo.NbaRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameMediaViewModel : ViewModel() {

    private val repo: NbaRepo = NbaRepo()

    fun getGameMedia(gameId: String): LiveData<List<Data>> {
        var tempList: List<Data> = emptyList()
        val gameMediaList: MutableLiveData<List<Data>> = MutableLiveData()

        viewModelScope.launch(Dispatchers.IO) {
            tempList = repo.getGameMedia(gameId)
        }.invokeOnCompletion {
            viewModelScope.launch {
                gameMediaList.value = tempList
            }
        }
        return gameMediaList
    }

}