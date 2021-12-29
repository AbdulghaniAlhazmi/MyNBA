package com.example.mynba.ui.gameMedia


import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynba.api.models.gameMedia.Data
import com.example.mynba.databinding.GameMediaFragmentBinding
import com.example.mynba.databinding.MediaListItemBinding
import com.example.mynba.ui.games.KEY_GAME_ID


class GameMediaFragment : Fragment() {

    private val gameMediaViewModel : GameMediaViewModel by lazy { ViewModelProvider(this)[GameMediaViewModel::class.java] }
    private lateinit var binding : GameMediaFragmentBinding
    private lateinit var gameId : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameId = requireArguments().getString(KEY_GAME_ID).toString()

        gameMediaViewModel.getGameMedia(gameId).observe(this,{
            binding.gameMediaRc.adapter = MediaAdapter(it)
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = GameMediaFragmentBinding.inflate(layoutInflater)
        binding.gameMediaRc.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    private inner class MediaHolder(val binding: MediaListItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(media : Data){

            binding.videoView2.setVideoURI(Uri.parse(media.url))

        }
    }

    private inner class MediaAdapter(val media : List<Data>) : RecyclerView.Adapter<MediaHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaHolder {
            val binding = MediaListItemBinding.inflate(
                layoutInflater,
                parent,
                false
            )
            return MediaHolder(binding)
        }

        override fun onBindViewHolder(holder: MediaHolder, position: Int) {
           val media = media[position]
            holder.bind(media)
        }

        override fun getItemCount(): Int = media.size

    }
}