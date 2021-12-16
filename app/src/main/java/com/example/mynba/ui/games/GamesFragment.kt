package com.example.mynba.ui.games

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mynba.api.models.nba.Game
import com.example.mynba.databinding.FragmentGamesBinding
import com.example.mynba.databinding.GamesListItemBinding
import com.vivekkaushik.datepicker.OnDateSelectedListener

import com.vivekkaushik.datepicker.DatePickerTimeline
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "GamesFragment"

class GamesFragment : Fragment() {

    private val gamesViewModel : GamesViewModel by lazy { ViewModelProvider(this)[GamesViewModel::class.java] }
    private lateinit var binding: FragmentGamesBinding


    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sdf = SimpleDateFormat("yyyy-M-dd")
        val currentDate = sdf.format(Date())
        Log.d(TAG,currentDate.toString())
        gamesViewModel.getGames(currentDate).observe(
            this, {
                binding.gamesRC.adapter = GamesAdapter(it)
            }
        )

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentGamesBinding.inflate(layoutInflater)
        binding.gamesRC.layoutManager = LinearLayoutManager(context)


        val datePickerTimeline: DatePickerTimeline = binding.datePickerTimeline
        datePickerTimeline.setInitialDate(2021, 10, 27)
        datePickerTimeline.setOnDateSelectedListener(object : OnDateSelectedListener {
            override fun onDateSelected(year: Int, month: Int, day: Int, dayOfWeek: Int) {
                var emonth = "${month+1}"
                var eday = "$day"
                if (month < 10){
                    emonth = "0${month + 1}"
                }
                if (day < 10){
                    eday = "0${day}"
                }
                val date = "$year-$emonth-${eday}"
                gamesViewModel.getGames(date).observe(
                    viewLifecycleOwner, {
                        binding.gamesRC.adapter = GamesAdapter(it)
                    }
                )
                Log.d(TAG,date)
            }

            override fun onDisabledDateSelected(
                year: Int,
                month: Int,
                day: Int,
                dayOfWeek: Int,
                isDisabled: Boolean
            ) {
            }

        })

        return binding.root
    }


    private inner class GamesHolder(val binding: GamesListItemBinding):RecyclerView.ViewHolder(binding.root) {

        fun bind(game: Game) {
            binding.awayLogo.load(game.vTeam.logo)
            binding.homeLogo.load(game.hTeam.logo)
            if (game.statusGame == "Scheduled"){
                binding.gameStatus.text = game.startTimeUTC
            }
            else{
                binding.gameStatus.text = game.statusGame
            }
            binding.hScore.text = game.hTeam.score.points
            binding.vScore.text = game.vTeam.score.points


        }
    }

    private inner class GamesAdapter(val game : List<Game>):RecyclerView.Adapter<GamesHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamesHolder {
            val binding = GamesListItemBinding.inflate(
                layoutInflater,
                parent,
                false
            )
            return GamesHolder(binding)
        }

        override fun onBindViewHolder(holder: GamesHolder, position: Int) {
            val game = game[position]
            holder.bind(game)
        }

        override fun getItemCount(): Int = game.size

    }


}