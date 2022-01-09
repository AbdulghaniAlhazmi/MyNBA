package com.example.mynba.ui.games

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
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
import com.example.mynba.*
import com.example.mynba.Notification
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
const val KEY_HOME_ID = "HOME_ID"
const val KEY_AWAY_ID = "AWAY_ID"
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
    private val sdf = SimpleDateFormat("yyyy-MM-dd")
    private var currentDate: String = sdf.format(Date())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sdf.timeZone = TimeZone.getTimeZone("Asia/Riyadh")
        currentDate = sdf.format(Date())
        Log.d(TAG, currentDate)
        gamesViewModel.getGames(currentDate).observe(
            this, {
                binding.gamesRc.adapter = GamesAdapter(it)
            }
        )

    }

    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentGamesBinding.inflate(layoutInflater)
        binding.gamesRc.layoutManager = LinearLayoutManager(context)

        createNotificationChanel()
//        binding.button2.setOnClickListener {
//            scheduleNotification()
//        }

//        binding.composeView.setContent {
//
//        }


        val datePickerTimeline: DatePickerTimeline = binding.datePickerTimeline
        datePickerTimeline.setInitialDate(2021,10,10)
        val date = Calendar.getInstance()
            date.add(Calendar.DATE,1)
        datePickerTimeline.setActiveDate(date)
        datePickerTimeline.setOnDateSelectedListener(object : OnDateSelectedListener {
            override fun onDateSelected(year: Int, month: Int, day: Int, dayOfWeek: Int) {
                var emonth = "${month + 1}"
                var eday = "$day"
                if (month <= 8) {
                    emonth = "0${emonth}"
                }
                if (day < 10) {
                    eday = "0${day}"
                }
                val date = "$year-$emonth-${eday}"
                gamesViewModel.getGames(date).observe(
                    viewLifecycleOwner, {
                        binding.gamesRc.adapter = GamesAdapter(it)
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

    @SuppressLint("SimpleDateFormat")
    private fun scheduleNotification(){

        val intent = Intent(activity?.applicationContext,Notification::class.java)
        val title = "Its Game Time"
        val message = "Game Time Now"
        intent.putExtra(TITLE_KEY,title)
        intent.putExtra(MESSAGE_KEY,message)

        val pendingIntent = PendingIntent.getBroadcast(
            activity?.applicationContext,
            NOTIFICATION_ID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-01-08 23:32:00")
        time.time
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time.time,
            pendingIntent
        )
    }


    private fun createNotificationChanel(){

        val name = "Notifi Chanel"
        val desc = "Chanel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID,name,importance)
        channel.description = desc
        val manager = activity?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }


    fun convertDate(date: String): String {

        var newDate = date
        newDate = newDate.replace(" ", "T")
        newDate += "Z"
        newDate = Instant.parse(newDate)
            .atZone(ZoneId.of("Asia/Riyadh"))
            .format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT))

        return newDate.substringAfter(",")
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
                    R.id.action_navigation_games_to_tabsFragment,
                    Bundle().apply {
                        putInt(KEY_GAME_ID, game.id)
                        putString(KEY_HOME_LOGO, game.home_team.logo)
                        putString(KEY_AWAY_LOGO, game.away_team.logo)
                        putString(KEY_HOME_SHORT, game.home_team.name_code)
                        putString(KEY_AWAY_SHORT, game.away_team.name_code)
                        putString(KEY_HOME_SCORE, game.home_score?.display.toString())
                        putString(KEY_AWAY_SCORE, game.away_score?.display.toString())
                        putInt(KEY_HOME_ID, game.home_team_id)
                        putInt(KEY_AWAY_ID, game.away_team_id)
                    })
            }
        }

        override fun getItemCount(): Int = game.size

    }

}
