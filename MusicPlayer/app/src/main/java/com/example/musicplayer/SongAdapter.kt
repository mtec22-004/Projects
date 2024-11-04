package com.example.musicplayer

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load


class SongAdapter(private val context: Context, private val songs: List<Song>) : RecyclerView.Adapter<SongViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_song_view, parent, false)
        return SongViewHolder(view)
    }

    override fun getItemCount(): Int {
      return songs.size
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songs[position]
      holder.SongTitile.text = song.title
        holder.SongArtist.text = song.artist
        holder.albumArt.load(song.artwork)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, SongDetailsActivity::class.java)
            intent.putExtra("selected_song",song)
            context.startActivity(intent)

        }
    }
}