package dev.graansma.tunebot_android

import android.media.MediaMetadataRetriever
import android.os.Build
import android.util.Log

data class Song(val url: String) {
    val title: String
    val album: String
    val artist: String
    val duration: String
    val image: String
    val year: String

    init {
        var mmr = if (url.isNotBlank() || url.isEmpty()) MediaMetadataRetriever() else null

        try {
            mmr?.setDataSource(url)
        } catch (e: Exception) {
            mmr = null
            Log.e("init-song", e.message ?: "failed to retrieve meta data for $url")
        }

        title = mmr?.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE) ?: ""
        album = mmr?.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM) ?: ""
        artist = mmr?.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST) ?: ""
        duration = mmr?.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION) ?: ""
        year = mmr?.extractMetadata(MediaMetadataRetriever.METADATA_KEY_YEAR) ?: ""
        image =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
                mmr?.extractMetadata(MediaMetadataRetriever.METADATA_KEY_IMAGE_PRIMARY) ?: ""
            else ""
    }

    override fun equals(other: Any?): Boolean {
        if(other is Song) {
            return other.url == url
        } else if(other is String) {
            return other == url
        }
        return super.equals(other)
    }

    override fun toString(): String {
        return "$title - $artist ($year) : $album"
    }
}