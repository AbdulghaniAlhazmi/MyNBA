package com.example.mynba.ui.gameMedia


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mynba.YOUTUBE_KEY
import com.example.mynba.YouTubePlayerActivity
import com.example.mynba.api.models.gameMedia.Data
import com.example.mynba.databinding.GameMediaFragmentBinding
import com.example.mynba.databinding.MediaListItemBinding
import com.example.mynba.ui.games.KEY_GAME_ID


private const val TAG = "GameMediaFragment"

class GameMediaFragment : Fragment() {

    private val gameMediaViewModel: GameMediaViewModel by lazy { ViewModelProvider(this)[GameMediaViewModel::class.java] }
    private lateinit var binding: GameMediaFragmentBinding
    private lateinit var gameId: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameId = requireArguments().getInt(KEY_GAME_ID).toString()

        gameMediaViewModel.getGameMedia(gameId).observe(this, {
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

    private inner class MediaHolder(val binding: MediaListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(media: Data) {

            binding.titleTv.text = media.sub_title
            binding.videoImage.load(media.thumbnail_url)
            var videoId = media.source_url
            videoId = videoId.substringAfter("watch?v=").substringBefore("&")
            Log.d(TAG, videoId)

        }
    }


    private inner class MediaAdapter(val media: List<Data>) : RecyclerView.Adapter<MediaHolder>() {
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

            holder.itemView.setOnClickListener {

                val intent = Intent(context, YouTubePlayerActivity::class.java).apply {
                    putExtra(YOUTUBE_KEY, media.url.substringAfter("watch?v=").substringBefore("&"))
                }
                startActivity(intent)

            }
        }

        override fun getItemCount(): Int = media.size

    }
}