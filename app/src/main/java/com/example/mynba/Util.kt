package com.example.mynba

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.startActivity
import com.google.android.material.snackbar.Snackbar


fun hideKeyboard(context : Context,view : View){
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(view.windowToken,0)
    }


    fun snackBarMaker(view: View, message: Int){
        Snackbar.make(view,message,Snackbar.LENGTH_LONG)
            .show()
    }


    fun toHome(context: Context){
        val intent = Intent(context, MainActivity::class.java)
        startActivity(context,intent, Bundle())
    }
