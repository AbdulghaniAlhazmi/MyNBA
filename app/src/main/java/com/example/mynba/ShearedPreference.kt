package com.example.mynba

import android.content.Context
import androidx.preference.PreferenceManager

const val HIDE_SCORE_KEY = "Hide"

object ShearedPreference {

    fun setHideScore(context: Context, hide : Boolean) {
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putBoolean(HIDE_SCORE_KEY,hide)
            .apply()
    }

    fun getHideScore(context: Context) : Boolean{
        val preferenceManager = PreferenceManager.getDefaultSharedPreferences(context)

        return preferenceManager.getBoolean(HIDE_SCORE_KEY,false)


    }

}