package com.example.mynba.ui.boxScore

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynba.api.models.boxScore.LineupPlayer
import com.example.mynba.databinding.BoxScoreFragmentBinding
import com.example.mynba.databinding.BoxscoreListItemBinding
import com.example.mynba.ui.games.KEY_GAME_ID
import com.example.mynba.ui.games.KEY_GAME_ID1
import com.example.mynba.ui.games.KEY_GAME_ID2
import kotlin.properties.Delegates

private const val TAG = "BoxScoreFragment"

class BoxScoreFragment : Fragment() {


    private val boxScoreViewModel : BoxScoreViewModel by lazy { ViewModelProvider(this)[BoxScoreViewModel::class.java] }
    private lateinit var binding : BoxScoreFragmentBinding
    private lateinit var gameId : String
    private var homeId by Delegates.notNull<Int>()
    private var awayId by Delegates.notNull<Int>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameId = requireArguments().getInt(KEY_GAME_ID).toString()
        homeId = requireArguments().getInt(KEY_GAME_ID1)
        awayId = requireArguments().getInt(KEY_GAME_ID2)

        boxScoreViewModel.getGameBoxScore(gameId,homeId).observe(
            this, {
                binding.boxScoreRC.adapter = BoxScoreAdapter(it)
            }
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = BoxScoreFragmentBinding.inflate(layoutInflater)
        binding.boxScoreRC.layoutManager = LinearLayoutManager(context)


        binding.homeButton.setOnClickListener {
            boxScoreViewModel.getGameBoxScore(gameId,homeId).observe(viewLifecycleOwner,{
                binding.boxScoreRC.adapter = BoxScoreAdapter(it)
            })
        }

        binding.awayButton.setOnClickListener {
            boxScoreViewModel.getGameBoxScore(gameId,awayId).observe(viewLifecycleOwner,{
                binding.boxScoreRC.adapter = BoxScoreAdapter(it)
            })
        }



        return binding.root
    }

    private inner class BoxScoreViewHolder(val binding : BoxscoreListItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind (boxScore : LineupPlayer){
            binding.player.text = boxScore.player.name_short
            binding.points.text = boxScore.position_key
            val minPlayed : Int = (boxScore.player_statistics.seconds_played % 3600) / 60
            binding.min.text = minPlayed.toString()
            Log.d(TAG,boxScore.player.name)

        }
    }

    private inner class BoxScoreAdapter(val boxScore: List<LineupPlayer>) : RecyclerView.Adapter<BoxScoreViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoxScoreViewHolder {
           val binding = BoxscoreListItemBinding.inflate(
               layoutInflater,
               parent,
               false
           )
            return BoxScoreViewHolder(binding)
        }

        override fun onBindViewHolder(holder: BoxScoreViewHolder, position: Int) {
            val boxScore = boxScore[position]
            holder.bind(boxScore)
        }

        override fun getItemCount(): Int = boxScore.size

    }

}