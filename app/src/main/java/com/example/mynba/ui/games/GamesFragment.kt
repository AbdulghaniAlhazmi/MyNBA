package com.example.mynba.ui.games

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mynba.R
import com.example.mynba.api.models.games.Data
import com.example.mynba.databinding.FragmentGamesBinding
import com.example.mynba.databinding.GamesListItemBinding
import com.vivekkaushik.datepicker.DatePickerTimeline
import com.vivekkaushik.datepicker.OnDateSelectedListener
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

private const val TAG = "GamesFragment"
const val KEY_GAME_ID = "GAME_ID"
const val KEY_HOME_LOGO = "HOME_LOGO"
const val KEY_AWAY_LOGO = "AWAY_LOGO"
const val KEY_HOME_SHORT = "HOME_SHORT"
const val KEY_AWAY_SHORT = "AWAY_SHORT"
const val KEY_HOME_SCORE = "HOME_SCORE"
const val KEY_AWAY_SCORE = "AWAY_SCORE"






class GamesFragment : Fragment() {

    private val gamesViewModel: GamesViewModel by lazy { ViewModelProvider(this)[GamesViewModel::class.java] }
    private lateinit var binding: FragmentGamesBinding

    @SuppressLint("SimpleDateFormat")
    private val sdf = SimpleDateFormat("yyyy-M-dd")
    private val currentDate: String = sdf.format(Date())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                var emonth = "${month + 1}"
                var eday = "$day"
                if (month < 10) {
                    emonth = "0${emonth}"
                }
                if (day < 10) {
                    eday = "0${day}"
                }
                val date = "$year-$emonth-${eday}"
                gamesViewModel.getGames(date).observe(
                    viewLifecycleOwner, {
                        binding.gamesRC.adapter = GamesAdapter(it)
                    }
                )
                Log.d(TAG, date)
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


    private inner class GamesHolder(val binding: GamesListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(game: Data) {
            binding.awayLogo.load(game.away_team.logo)
            binding.homeLogo.load(game.home_team.logo)
            if (game.status_more == "-") {
                binding.gameStatus.text = convertDate(game.start_at)
            } else {
                binding.gameStatus.text = game.status_more
            }
            if (game.home_score == null) {
                binding.hScore.text = ""
            } else {
                binding.hScore.text = game.home_score.display.toString()
            }
            if (game.away_score == null) {
                binding.vScore.text = ""
            } else {
                binding.vScore.text = game.away_score.display.toString()
            }


        }
    }

    fun convertDate(date : String) : String{

        var newDate = date
        newDate = newDate.replace(" ","T")
        newDate += "Z"
        newDate = Instant.parse(newDate)
            .atZone(ZoneId.of("Asia/Riyadh"))
            .format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT))

        return newDate
    }

    private inner class GamesAdapter(val game: List<Data>) : RecyclerView.Adapter<GamesHolder>() {
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
            holder.itemView.setOnClickListener {
                Log.d(TAG, game.id.toString())

                findNavController().navigate(
                    R.id.action_navigation_games_to_gameStatusFragment,
                    Bundle().apply {
                        putInt(KEY_GAME_ID, game.id)
                        putString(KEY_HOME_LOGO,game.home_team.logo)
                        putString(KEY_AWAY_LOGO,game.away_team.logo)
                        putString(KEY_HOME_SHORT,game.home_team.name_code)
                        putString(KEY_AWAY_SHORT,game.away_team.name_code)
                        putString(KEY_HOME_SCORE, game.home_score?.display.toString())
                        putString(KEY_AWAY_SCORE, game.away_score?.display.toString())

                    })
            }

        }

        override fun getItemCount(): Int = game.size

    }

    override fun onResume() {
        super.onResume()

        gamesViewModel.getGames(currentDate).observe(
            this, {
                binding.gamesRC.adapter = GamesAdapter(it)
            }
        )

    }

}