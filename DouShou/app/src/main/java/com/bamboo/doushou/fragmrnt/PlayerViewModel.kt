package com.bamboo.doushou.fragmrnt

import android.app.Application
import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.lifecycle.*
import com.bamboo.doushou.R
import com.bamboo.doushou.datarepository.Video
import com.bamboo.doushou.datarepository.VideoRepository
import kotlinx.coroutines.launch
import java.security.cert.CertPath
import kotlin.properties.Delegates

class PlayerViewModel(application: Application) :AndroidViewModel(application) {
    val videoRepository = VideoRepository(application)
    var surfaceViewStatus = false
    private val _bufferPercent = MutableLiveData(0)
    val bufferPercent:LiveData<Int> = _bufferPercent
    private val _progressBarVisibility = MutableLiveData(View.VISIBLE)
    val progressBarVisibility:LiveData<Int> = _progressBarVisibility
    var videoStatus = false
    var presentStatus:Boolean = false
    val myMedia = MediaPlayer()
    var videoPath = "android.resource://com.bamboo.doushou/${R.raw.eilina}"
    //lateinit var video:Video
    val video = MutableLiveData(Video())
    var videoID = 0

    /*init {
        loadVideo()
    }
     */
    fun loadVideo(){
        myMedia.apply {
            viewModelScope.launch {
                video.value = videoRepository.getVideoByID(videoID+1)[0]
                Log.d("repository","video's liked ${video.value!!.liked}")
            }
            //setDataSource(getApplication(), Uri.parse("android.resource://com.bamboo.doushou/${R.raw.eilina}"))
            setDataSource(getApplication(), Uri.parse(videoPath))
            setOnPreparedListener{

                _progressBarVisibility.value = View.INVISIBLE
                it.seekTo(1)
                videoStatus = true
                //it.start()//不能立即播放，不然全部播放

            }
            setOnSeekCompleteListener {
                //Log.d("TiaoYiTiao","setOnSeekCompleteListener")
                _progressBarVisibility.value = View.INVISIBLE
            }
            setOnBufferingUpdateListener { _, percent ->
                _bufferPercent.value = percent
            }
            prepareAsync()
        }
    }
    override fun onCleared() {
        super.onCleared()
        myMedia.release()
        Log.d("whatHappened","onClear")
    }

    fun playerSeekToProgress(progress:Int){
        _progressBarVisibility.value = View.VISIBLE
        myMedia.seekTo(progress)
    }
    fun insertVideos(vararg  video:Video) = viewModelScope.launch {
        videoRepository.insertVideo(*video)
    }
    fun updateVideo(video: Video){
        viewModelScope.launch {
            videoRepository.updateVideo(video)
        }
    }

}