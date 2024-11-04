package com.example.pama

// PlayMusicActivity.kt

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class PlayMusicActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_player)

        mediaPlayer = MediaPlayer.create(this, R.raw.ride) // Replace with your music file

        val buttonPlay: Button = findViewById(R.id.buttonPlay)
        val buttonPause: Button = findViewById(R.id.buttonPause)
        val buttonStop: Button = findViewById(R.id.buttonStop)

        buttonPlay.setOnClickListener { playMusic() }
        buttonPause.setOnClickListener { pauseMusic() }
        buttonStop.setOnClickListener { stopMusic() }
    }

    fun playMusic() {
        mediaPlayer?.start()
    }

    fun pauseMusic() {
        mediaPlayer?.pause()
    }

    fun stopMusic() {
        mediaPlayer?.apply {
            stop()
            prepare()
            seekTo(0)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
