package com.example.mynba.ui.gameStatus

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mynba.api.models.gameStatus.Game
import com.example.mynba.databinding.GameStatusFragmentBinding
import com.example.mynba.databinding.GameStatusItemBinding
import com.example.mynba.ui.games.KEY_GAME_ID

private const val TAG = "GameStatusFragment"

class GameStatusFragment : Fragment() {


        private val gameStatusViewModel : GameStatusViewModel by lazy { ViewModelProvider(this)[GameStatusViewModel::class.java] }
        private lateinit var binding: GameStatusFragmentBinding
        private lateinit var gameId : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameId = arguments?.getString(KEY_GAME_ID).toString()

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
        binding.statusRC.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    private inner class StatusHolder(val binding: GameStatusItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind (status : Game){
            binding.textView.text = status.hTeam.nickname
            binding.textView2.text = status.vTeam.nickname
            binding.imageView2.load(status.hTeam.logo)
            binding.imageView4.load(status.vTeam.logo)
        }

    }

    private inner class StatusAdapter(val status : List<Game>):RecyclerView.Adapter<StatusHolder>(){
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
