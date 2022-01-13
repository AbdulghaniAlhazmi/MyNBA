package com.example.mynba

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.mynba.databinding.ActivityYouTubePlayerBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

const val YOUTUBE_KEY = "KEY"

class YouTubePlayerActivity : AppCompatActivity() {

    lateinit var binding: ActivityYouTubePlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYouTubePlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val videoId = intent.getStringExtra(YOUTUBE_KEY)

        binding.youTubePlayer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(@NonNull youTubePlayer: YouTubePlayer) {
                if (videoId != null) {
                    youTubePlayer.cueVideo(videoId, 0f)
                }
            }
        })

    }
}