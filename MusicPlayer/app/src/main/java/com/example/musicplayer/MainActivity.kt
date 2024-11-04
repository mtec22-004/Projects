package com.example.musicplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    lateinit var songAdapter: SongAdapter
    private lateinit var songList: List<Song>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        songList = listOf(
            Song("Bank Account", "Issa", 2017, "21 Savage","Hip Hop", R.drawable.issa),
            Song("Starboy", "Starboy", 2016, "The Weeknd","Hip Hop", R.drawable.boy),
            Song("Had Enough", "Jackboys", 2020, "Jackboys","Hip Hop", R.drawable.jackboys)



        )
        recyclerView = findViewById(R.id.list)
        songAdapter = SongAdapter(this,songList)
        recyclerView.adapter = songAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)


    }
}