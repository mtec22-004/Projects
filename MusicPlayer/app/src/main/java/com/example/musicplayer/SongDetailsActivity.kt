package com.example.musicplayer

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView

class SongDetailsActivity : AppCompatActivity() {
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var songTitle: String
    private lateinit var songAlbum: String
    private lateinit var songYear: String
    private lateinit var songArtist: String
    private lateinit var songGenre: String
    private var songArtwork: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_details)

       val songTitleTextView = findViewById<TextView>(R.id.songtitle)
        val songArtistTextView = findViewById<TextView>(R.id.songartist)
        val songAlbumTextView = findViewById<TextView>(R.id.songalbum)
        val songYearTextView = findViewById<TextView>(R.id.songyear)
        val songGenreTextView = findViewById<TextView>(R.id.songgenre)
        val songArtworkImageView = findViewById<ImageView>(R.id.artwork)

        val song = intent.getParcelableExtra<Song>("selected_song")
        val playPauseButton = findViewById<ImageButton>(R.id.play_pause)
        val stopButton = findViewById<ImageButton>(R.id.stop)
    if(song != null) {
        songTitle = song.title.toString()
        songAlbum = song.album.toString()
        songYear = song.year.toString()
        songArtist = song.artist.toString()
        songGenre = song.genre.toString()
        songArtwork = song.artwork
        mediaPlayer = MediaPlayer.create(this, R.raw.starboy)
        mediaPlayer?.setOnPreparedListener { it.start() }
        playPauseButton.setImageResource(R.drawable.baseline_pause_24)
    }else
        mediaPlayer?.setOnErrorListener { mp, what, extra ->
            Log.e("Media Player", "Error occured: what= $what. extra=$extra")
            false
        }
songTitleTextView.text = songTitle
        songArtistTextView.text = songArtist
        songAlbumTextView.text = songAlbum
        songYearTextView.text = songYear
        songGenreTextView.text = songGenre
        songArtworkImageView.setImageResource(songArtwork)

        playPauseButton.setOnClickListener {
            if(mediaPlayer?.isPlaying == true) {
                mediaPlayer?.pause()
                playPauseButton.setImageResource(R.drawable.baseline_play_arrow_24)

            } else {
                mediaPlayer?.start()
                playPauseButton.setImageResource(R.drawable.baseline_pause_24)
            }
        }
        stopButton.setOnClickListener {
            if (mediaPlayer?.isPlaying==true) {
                mediaPlayer?.stop()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
    }
}