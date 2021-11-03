package com.bamboo.doushou.fragmrnt

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.bamboo.doushou.datarepository.Video
import com.bamboo.doushou.datarepository.VideoRepository
import kotlinx.coroutines.launch

class AccountViewModel(application:Application) :AndroidViewModel(application) {
    private val videoRepository:VideoRepository = VideoRepository(application)
    val allVideo:LiveData<List<Video>> = videoRepository.getAllVideoLiveData()
    fun insertVideos(vararg video: Video) = viewModelScope.launch {
            videoRepository.insertVideo(*video)
        }
    fun deleteVideos(vararg  video: Video) = viewModelScope.launch {
        videoRepository.deleteVideo(*video)
    }
    fun updateVideos(vararg  video: Video) = viewModelScope.launch {
        videoRepository.updateVideo(*video)
    }
    fun clearVideo(vararg  video: Video) = viewModelScope.launch {
        videoRepository.clearAllVideo()
    }

}