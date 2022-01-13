package com.example.mynba

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.NonNull
import com.example.mynba.databinding.ActivityMainBinding
import com.example.mynba.databinding.ActivityYouTubePlayerBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class YouTubePlayerActivity : AppCompatActivity() {

    lateinit var binding: ActivityYouTubePlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYouTubePlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val videoId = intent.getStringExtra("KEY")

        binding.youTubePlayer.getPlayerUiController().showFullscreenButton(true)
        binding.youTubePlayer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener(){
            override fun onReady(@NonNull youTubePlayer: YouTubePlayer) {
                if (videoId != null) {
                    youTubePlayer.cueVideo(videoId,0f)
                }
            }
        })

        binding.youTubePlayer.getPlayerUiController().setFullScreenButtonClickListener {
            if (binding.youTubePlayer.isFullScreen()) {
                binding.youTubePlayer.exitFullScreen()
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
                supportActionBar?.show()
            } else {
                binding.youTubePlayer.enterFullScreen()
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
                supportActionBar?.hide()

            }
        }


    }
}