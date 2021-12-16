package com.example.mynba.ui.games

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mynba.api.models.nba.Game
import com.example.mynba.databinding.FragmentGamesBinding
import com.example.mynba.databinding.GamesListItemBinding
import com.vivekkaushik.datepicker.OnDateSelectedListener

import com.vivekkaushik.datepicker.DatePickerTimeline
import java.util.*

private const val TAG = "GamesFragment"

class GamesFragment : Fragment() {

    private val gamesViewModel : GamesViewModel by lazy { ViewModelProvider(this)[GamesViewModel::class.java] }
    private lateinit var binding: FragmentGamesBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gamesViewModel.getGames().observe(
            this, Observer {
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

        binding.datePickerTimeline

        val datePickerTimeline: DatePickerTimeline = binding.datePickerTimeline
        datePickerTimeline.setInitialDate(2021, 11, 16)
        datePickerTimeline.setOnDateSelectedListener(object : OnDateSelectedListener {
            override fun onDateSelected(year: Int, month: Int, day: Int, dayOfWeek: Int) {
                val date = "$year-${month + 1}-$day"
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

        val dates: Array<Date> = arrayOf<Date>(Calendar.getInstance().getTime())
        datePickerTimeline.deactivateDates(dates)

        return binding.root
    }


    private inner class GamesHolder(val binding: GamesListItemBinding):RecyclerView.ViewHolder(binding.root) {

        fun bind(game: Game) {
            binding.awayLogo.load(game.vTeam.logo)
            binding.homeLogo.load(game.hTeam.logo)
            binding.gameStatus.text = game.statusGame
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