package com.example.mynba.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynba.api.models.webSearch.Value
import com.example.mynba.api.repo.NbaRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel: ViewModel() {

    private val repo: NbaRepo = NbaRepo()

    fun getNews(): LiveData<List<Value>> {
        var tempList: List<Value> = emptyList()
        val newsLiveData: MutableLiveData<List<Value>> = MutableLiveData()

        viewModelScope.launch(Dispatchers.IO) {
            tempList = repo.getNews()
        }.invokeOnCompletion {
            viewModelScope.launch {
                newsLiveData.value = tempList
            }
        }
        return newsLiveData
    }
}

