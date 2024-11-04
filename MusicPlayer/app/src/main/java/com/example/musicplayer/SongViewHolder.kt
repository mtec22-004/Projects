package com.example.musicplayer

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val albumArt : ImageView = itemView.findViewById(R.id.art)
    val SongTitile: TextView = itemView.findViewById(R.id.title)
    val SongArtist: TextView = itemView.findViewById(R.id.artist)
}