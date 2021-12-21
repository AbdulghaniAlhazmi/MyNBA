package com.example.mynba.ui.gameStatus

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mynba.api.models.gameStatus.Data
import com.example.mynba.databinding.GameStatusFragmentBinding
import com.example.mynba.databinding.GameStatusItemBinding
import com.example.mynba.ui.games.*
import kotlin.properties.Delegates

private const val TAG = "GameStatusFragment"

class GameStatusFragment : Fragment() {


        private val gameStatusViewModel : GameStatusViewModel by lazy { ViewModelProvider(this)[GameStatusViewModel::class.java] }
        private lateinit var binding: GameStatusFragmentBinding
        private lateinit var gameId : String
        private lateinit var homeLogo : String
        private lateinit var awayLogo : String
        private lateinit var homeShort : String
        private lateinit var awayShort : String







    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         awayShort= requireArguments().getString(KEY_GAME_ID3).toString()
        homeShort = requireArguments().getString(KEY_GAME_ID4).toString()

        (activity as AppCompatActivity).supportActionBar?.title = "$awayShort - $homeShort"
        gameId = requireArguments().getInt(KEY_GAME_ID).toString()
        homeLogo = requireArguments().getString(KEY_GAME_ID1).toString()
        awayLogo = requireArguments().getString(KEY_GAME_ID2).toString()
        gameStatusViewModel.getGamesStatus(gameId).observe(
            this,{
                binding.statusRC.adapter = StatusAdapter(it)
            }
        )
        Log.d(TAG,"$gameId from status")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = GameStatusFragmentBinding.inflate(layoutInflater)
        binding.awayLogo.load(awayLogo)
        binding.homeLogoS.load(homeLogo)
        binding.statusRC.layoutManager = LinearLayoutManager(context)


        return binding.root
    }

    private inner class StatusHolder(val binding: GameStatusItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(status: Data) {
            binding.name.text = status.name
            binding.homeS.text = status.home
            binding.awayS.text = status.away

        }
    }

    private inner class StatusAdapter(val status : List<Data>):RecyclerView.Adapter<StatusHolder>(){
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
