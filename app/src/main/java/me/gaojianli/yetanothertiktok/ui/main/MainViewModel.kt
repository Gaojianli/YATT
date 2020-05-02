package me.gaojianli.yetanothertiktok.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import me.gaojianli.yetanothertiktok.data.VideoRepository

class MainViewModel : ViewModel() {
    private val repository: VideoRepository = VideoRepository()
    val videoList = liveData(Dispatchers.IO) {
        val videoList = repository.getVideoList()
        emit(videoList)
    }
    // TODO: Implement the ViewModel
}