package com.example.mynba.ui.gameStatus

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mynba.R
import com.example.mynba.api.models.gameStatus.Data
import com.example.mynba.databinding.GameStatusFragmentBinding
import com.example.mynba.databinding.GameStatusItemBinding
import com.example.mynba.ui.games.*
import java.util.*

private const val TAG = "GameStatusFragment"

class GameStatusFragment : Fragment() {


    private val gameStatusViewModel: GameStatusViewModel by lazy { ViewModelProvider(this)[GameStatusViewModel::class.java] }
    private lateinit var binding: GameStatusFragmentBinding
    private lateinit var gameId: String
    private lateinit var homeLogo: String
    private lateinit var awayLogo: String
    private lateinit var homeShort: String
    private lateinit var awayShort: String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameId = requireArguments().getInt(KEY_GAME_ID).toString()
        homeLogo = requireArguments().getString(KEY_HOME_LOGO).toString()
        awayLogo = requireArguments().getString(KEY_AWAY_LOGO).toString()
        homeShort = requireArguments().getString(KEY_HOME_SHORT)?: ""
        awayShort = requireArguments().getString(KEY_AWAY_SHORT)?:""




        (activity as AppCompatActivity).supportActionBar?.title =
            "$awayShort - $homeShort"

        gameStatusViewModel.getGamesStatus(gameId).observe(
            this, {
                binding.statusRc.adapter = StatusAdapter(it)
            }
        )
        Log.d(TAG, "$gameId from status")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = GameStatusFragmentBinding.inflate(layoutInflater)
        binding.awayLogo.load(awayLogo)
        binding.homeLogo.load(homeLogo)
        binding.statusRc.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    private inner class StatusHolder(val binding: GameStatusItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(status: Data) {
            binding.status.text = status.name.replace("_", " ")
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
            binding.homeStatus.text = status.home
            binding.awayStatus.text = status.away

            var home = 0F
            var away = 0F
            when {
                status.home.contains("%") -> {
                    home = status.home.substringAfter("(").substringBeforeLast(")").replace("%","").toFloat()
                    away = status.away.substringAfter("(").substringBeforeLast(")").replace("%","").toFloat()
                }
                else -> {
                    home = status.home.toFloat()
                    away = status.away.toFloat()
                }
            }

            binding.percentageProgressBar.setValues(away, home)


        }
    }

    private inner class StatusAdapter(val status: List<Data>) :
        RecyclerView.Adapter<StatusHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatusHolder {
            val binding = GameStatusItemBinding.inflate(
                layoutInflater,
                parent,
                false
            )
            return StatusHolder(binding)
        }

        override fun onBindViewHolder(holder: StatusHolder, position: Int) {
            val status = status[position]
            holder.bind(status)
        }

        override fun getItemCount(): Int = status.size

    }

}
