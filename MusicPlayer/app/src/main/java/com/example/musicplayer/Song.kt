package com.example.musicplayer



import android.os.Parcel
import android.os.Parcelable

data class Song(
    val title: String?,
    val album: String?,
    val year:Int,
    val artist: String?,
    val genre: String?,
    val artwork:Int
):Parcelable{
    constructor(parcel: Parcel):this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(album)
        parcel.writeInt(year)
        parcel.writeString(artist)
        parcel.writeString(genre)
        parcel.writeInt(artwork)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Song> {
        override fun createFromParcel(parcel: Parcel): Song {
            return Song(parcel)
        }

        override fun newArray(size: Int): Array<Song?> {
            return arrayOfNulls(size)
        }
    }
}