package com.example.mynba.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynba.database.DataBaseRepo
import com.example.mynba.database.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


private const val TAG = "SignUpViewModel"

class SignUpViewModel : ViewModel() {

    private val dataBaseRepo = DataBaseRepo()

     fun createUser(user: User, password : String){
         viewModelScope.launch(Dispatchers.IO){
            dataBaseRepo.createUser(user,password)
        }.invokeOnCompletion {
            viewModelScope.launch {}
        }
     }


}