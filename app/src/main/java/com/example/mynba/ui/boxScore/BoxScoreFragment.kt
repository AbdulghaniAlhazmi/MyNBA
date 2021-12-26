package com.example.mynba.ui.boxScore

import android.annotation.SuppressLint
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
import com.example.mynba.api.models.boxScore.LineupPlayer
import com.example.mynba.databinding.BoxScoreFragmentBinding
import com.example.mynba.databinding.BoxscoreListItemBinding
import com.example.mynba.ui.games.*
import kotlin.properties.Delegates

private const val TAG = "BoxScoreFragment"

class BoxScoreFragment : Fragment() {


    private val boxScoreViewModel : BoxScoreViewModel by lazy { ViewModelProvider(this)[BoxScoreViewModel::class.java] }
    private lateinit var binding : BoxScoreFragmentBinding
    private lateinit var gameId : String
    private var homeId by Delegates.notNull<Int>()
    private var awayId by Delegates.notNull<Int>()
    private lateinit var homeShort : String
    private lateinit var awayShort : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameId = requireArguments().getInt(KEY_GAME_ID).toString()
        homeId = requireArguments().getInt(KEY_HOME_ID)
        awayId = requireArguments().getInt(KEY_AWAY_ID)
        awayShort = requireArguments().getString(KEY_AWAY_SHORT).toString()
        homeShort = requireArguments().getString(KEY_HOME_SHORT).toString()

        (activity as AppCompatActivity).supportActionBar?.title = "$awayShort - $homeShort"

        observeStarter(gameId,homeId)
        observeBench(gameId,homeId)

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = BoxScoreFragmentBinding.inflate(layoutInflater)
        binding.boxScoreRC.layoutManager = LinearLayoutManager(context)
        binding.benchRC.layoutManager = LinearLayoutManager(context)
        binding.homeButton.text = homeShort
        binding.awayButton.text = awayShort

        binding.homeButton.setOnClickListener {
            observeStarter(gameId,homeId)
            observeBench(gameId,homeId)
        }

        binding.awayButton.setOnClickListener {
            observeStarter(gameId,awayId)
            observeBench(gameId,awayId)
        }

        return binding.root
    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun observeStarter(gameId : String, teamId: Int){
        boxScoreViewModel.getGameBoxScore(gameId,teamId,false).observe(this,{
            binding.boxScoreRC.adapter = BoxScoreAdapter(it)
        })
    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun observeBench(gameId: String, teamId: Int){
        boxScoreViewModel.getGameBoxScore(gameId,teamId,true).observe(this,{
            binding.benchRC.adapter = BoxScoreAdapter(it)
        })
    }

    private inner class BoxScoreViewHolder(val binding : BoxscoreListItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind (boxScore : LineupPlayer){
            binding.player.text = boxScore.player.name_short
            if (boxScore.substitute){
                binding.position.text = " "
            }
            else{
                binding.position.text = boxScore.position_key
            }
            val minPlayed : Int = (boxScore.player_statistics.seconds_played % 3600) / 60
            binding.min.text = minPlayed.toString()
            Log.d(TAG,boxScore.player.name)
            binding.points.text = boxScore.player_statistics.points.toString()

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