package com.bamboo.doushou.datarepository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VideoRepository(context: Context) {
    private val videoDao:VideoDao
    private val videoLiveData:LiveData<List<Video>>
    init {
        videoDao = VideoDatabase.getVideoDatabase(context).getVideoDao()
        videoLiveData = videoDao.getAllVideoLiveData()
    }
    fun getAllVideoLiveData():LiveData<List<Video>>{return videoLiveData}
    suspend fun getAllVideoList():List<Video>{
        return withContext(Dispatchers.IO){
            Log.d("inittt","start")
            videoDao.getAllVideoList()
        }
    }
    suspend fun insertVideo(vararg video: Video){
        withContext(Dispatchers.IO){
            videoDao.insertVideos(*video)
        }
    }
    suspend fun deleteVideo(vararg video: Video){
        withContext(Dispatchers.IO){
            videoDao.deleteVideos(*video)
        }
    }
    suspend fun updateVideo(vararg video: Video){
        withContext(Dispatchers.IO){
            videoDao.updateVideos(*video)
        }
    }
    suspend fun getVideoByID(id:Int):List<Video>{
        //Log.d("repository","$id")
        return withContext(Dispatchers.IO){
            videoDao.getVideoByID(id)
        }
    }
    fun clearAllVideo(){
        videoDao.clearVideos()
    }
}