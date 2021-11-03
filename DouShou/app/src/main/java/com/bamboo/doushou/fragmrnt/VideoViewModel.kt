package com.bamboo.doushou.fragmrnt

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bamboo.doushou.datarepository.Video

import com.bamboo.doushou.datarepository.VideoRepository
import kotlinx.coroutines.launch

class VideoViewModel(application: Application) : AndroidViewModel(application) {
    private val videoRepository = VideoRepository(application)
    val playerList = mutableListOf<PlayerFragment>()
    val videoListLiveData = videoRepository.getAllVideoLiveData()
    lateinit var videoList:List<Video>
    init {
        viewModelScope.launch {
            videoList = videoRepository.getAllVideoList()
            Log.d("inittt","here")
        }
    }
}