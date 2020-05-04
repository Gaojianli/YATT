package me.gaojianli.yetanothertiktok.ui.main.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import me.gaojianli.yetanothertiktok.data.VideoRepository
import me.gaojianli.yetanothertiktok.data.VideoResponse
import me.gaojianli.yetanothertiktok.utils.MediaUtils

class MainViewModel : ViewModel() {
    private val repository: VideoRepository = VideoRepository()
    val videoList = liveData(Dispatchers.IO) {
        val videoList = repository.getVideoList()
        emit(videoList)
    }

    fun getVideoPreview(video: VideoResponse) = MediaUtils.getVideoPreview(video.url)
}