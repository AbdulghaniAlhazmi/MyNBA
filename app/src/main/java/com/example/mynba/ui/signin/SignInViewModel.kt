package com.example.mynba.ui.signin

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynba.database.DataBaseRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel() {

    private var dataBaseRepo  = DataBaseRepo()


    fun signInUser(email : String , password : String){
        viewModelScope.launch(Dispatchers.IO){
            dataBaseRepo.signInUser(email, password)
        }.invokeOnCompletion {
            viewModelScope.launch {}
        }
    }




}