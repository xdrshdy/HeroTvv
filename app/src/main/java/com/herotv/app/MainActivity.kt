package com.herotv.app

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

class MainActivity : AppCompatActivity() {

    private var player: ExoPlayer? = null
    private lateinit var playerView: PlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val rootLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
        }

        val inputUrl = EditText(this).apply {
            hint = "ضع رابط IPTV هنا (.m3u8 / .mp4)"
        }

        val playButton = Button(this).apply {
            text = "تشغيل البث"
        }

        playerView = PlayerView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        }

        rootLayout.addView(inputUrl)
        rootLayout.addView(playButton)
        rootLayout.addView(playerView)
        setContentView(rootLayout)

        player = ExoPlayer.Builder(this).build()
        playerView.player = player

        playButton.setOnClickListener {
            val url = inputUrl.text.toString().trim()
            if (url.isNotEmpty()) {
                val mediaItem = MediaItem.fromUri(Uri.parse(url))
                player?.setMediaItem(mediaItem)
                player?.prepare()
                player?.play()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        player?.release()
        player = null
    }
}