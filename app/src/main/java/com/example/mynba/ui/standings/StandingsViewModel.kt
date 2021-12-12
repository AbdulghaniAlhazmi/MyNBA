package com.example.mynba.ui.standings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StandingsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Standings Fragment"
    }
    val text: LiveData<String> = _text
}