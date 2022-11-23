package dev.graansma.tunebot_android

import android.media.MediaMetadataRetriever

class Song(val url: String) {
    val title: String
    val album: String
    val artist: String
    val duration: String
    val image: String
    val year: String

    init {
        val mmr = if(url.isNotBlank() || url.isEmpty()) MediaMetadataRetriever() else null
        mmr?.setDataSource(url)
        title = mmr?.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE) ?: ""
        album = mmr?.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM) ?: ""
        artist = mmr?.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST) ?: ""
        duration = mmr?.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION) ?: ""
        image = mmr?.extractMetadata(MediaMetadataRetriever.METADATA_KEY_IMAGE_PRIMARY) ?: ""
        year = mmr?.extractMetadata(MediaMetadataRetriever.METADATA_KEY_YEAR) ?: ""
    }

    override fun equals(other: Any?): Boolean {
        if(other is Song) {
            return other.url == url
        } else if(other is String) {
            return other == url
        }
        return super.equals(other)
    }
}