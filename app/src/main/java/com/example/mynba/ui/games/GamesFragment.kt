package com.example.mynba.ui.games

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mynba.*
import com.example.mynba.R
import com.example.mynba.api.models.games.Data
import com.example.mynba.databinding.FragmentGamesBinding
import com.example.mynba.databinding.GamesListItemBinding
import com.example.mynba.datePicker.Blue
import com.example.mynba.datePicker.DatePickerTimeLineTheme
import com.example.mynba.datePicker.Grey
import com.example.mynba.datePicker.White
import com.foreverrafs.datepicker.DatePickerTimeline
import com.foreverrafs.datepicker.Orientation
import com.foreverrafs.datepicker.state.rememberDatePickerState
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlin.properties.Delegates


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
private var hideScore by Delegates.notNull<Boolean>()



class GamesFragment : Fragment() {

    private val gamesViewModel: GamesViewModel by lazy { ViewModelProvider(this)[GamesViewModel::class.java] }
    private lateinit var binding: FragmentGamesBinding
    private var date = LocalDate.now().toString()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, date)
        val sharedPrefs: SharedPreferences? = activity?.getSharedPreferences("com.example.mynba", Context.MODE_PRIVATE)
        if (sharedPrefs != null) {
            hideScore = sharedPrefs.getBoolean("HideScore", false)
        }
        Log.d(TAG, hideScore.toString())

        gamesViewModel.getGames(date).observe(
            this, {
                binding.gamesRc.adapter = GamesAdapter(it)
            }
        )

    }

    @SuppressLint("SimpleDateFormat", "CommitPrefEdits")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentGamesBinding.inflate(layoutInflater)
        binding.gamesRc.layoutManager = LinearLayoutManager(context)

        if (hideScore){
            binding.hideScore.isChecked = true
        }
        binding.hideScore.setOnClickListener {
            if (binding.hideScore.isChecked){
                hideScore = true
                    val editor : SharedPreferences.Editor? =
                        activity?.getSharedPreferences("com.example.mynba",Context.MODE_PRIVATE)
                            ?.edit()
                editor?.putBoolean("HideScore", hideScore)
                editor?.apply()
            }else{
                hideScore = false
                val editor : SharedPreferences.Editor? =
                    activity?.getSharedPreferences("com.example.mynba",Context.MODE_PRIVATE)
                        ?.edit()
                editor?.putBoolean("HideScore", hideScore)
                editor?.apply()
            }
        }

//        createNotificationChanel()
//        binding.button.setOnClickListener {
//            scheduleNotification()
//        }

        binding.composeView.setContent {
                datePick()
        }

        return binding.root
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun datePick() {
        DatePickerTimeLineTheme {
            Surface(
                modifier = Modifier.wrapContentSize(),
            ){
                Column(
                    modifier = Modifier.padding(8.dp).fillMaxSize()
                ) {
                    val datePickerState = rememberDatePickerState(initialDate = LocalDate.now())

                    val mainBackgroundColor by remember { mutableStateOf(White)}
                    val selectedDateBackground by remember { mutableStateOf(Grey)}
                    val dateTextColor by remember { mutableStateOf(Blue) }

                    DatePickerTimeline(
                        modifier = Modifier.wrapContentSize(),
                        onDateSelected = { selectedDate ->
                            Log.d(TAG,selectedDate.toString())
                            gamesViewModel.getGames(selectedDate.toString()).observe(
                                viewLifecycleOwner, {
                                    binding.gamesRc.adapter = GamesAdapter(it)
                                })
                        },
                        backgroundColor = mainBackgroundColor,
                        orientation = Orientation.Horizontal,
                        state = datePickerState,
                        selectedBackgroundColor = selectedDateBackground,
                        dateTextColor = dateTextColor,
                        selectedTextColor = Blue,
                        todayLabel = {
                            Log.d(TAG,date.toString())
                            Text(
                                modifier = Modifier.padding(10.dp),
                                text = "Today",
                                color = Blue,
                                style = MaterialTheme.typography.h6
                            )
                        },
                        pastDaysCount = 120,
                    )
                }
            }
        }
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
           if (hideScore){
               binding.hScore.text = "-"
               binding.vScore.text = "-"
           }else{
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

//            binding.notify.setOnClickListener {
//                scheduleNotification(game.start_at)
//                Snackbar.make(requireView(), getString(R.string.fileduser), Snackbar.LENGTH_LONG).show()
//            }



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
            notificationId,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-01-10 00:26:00")
        time.time
        Log.d(TAG,time.toString())
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
            createNotificationChanel()
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
