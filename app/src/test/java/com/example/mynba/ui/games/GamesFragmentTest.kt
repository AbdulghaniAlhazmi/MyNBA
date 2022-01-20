package com.example.mynba.ui.games

import org.junit.Assert.*
import org.junit.Test

class GamesFragmentTest{


    @Test
    fun convertDateTest(){
        val result = GamesFragment().convertDate("2022-01-11 00:30:00")
        assertEquals(result," 3:30 AM")
    }

}
