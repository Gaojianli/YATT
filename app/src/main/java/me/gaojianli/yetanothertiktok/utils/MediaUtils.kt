package me.gaojianli.yetanothertiktok.utils

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever

object MediaUtils {
    fun getVideoPreview(url: String): Bitmap {
        val mediaMetadataRetriever = MediaMetadataRetriever()
        mediaMetadataRetriever.setDataSource(url, HashMap())
        return mediaMetadataRetriever.frameAtTime
    }

}