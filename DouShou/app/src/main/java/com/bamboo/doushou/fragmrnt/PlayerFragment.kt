package com.bamboo.doushou.fragmrnt

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.bamboo.doushou.R
import com.bamboo.doushou.datarepository.Video
import kotlinx.android.synthetic.main.liked_icon_layout.*
import kotlinx.android.synthetic.main.player_fragment.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class PlayerFragment(private val VideoPath:String?,private val position:Int) : Fragment() {//类名后加上括号才能用次级构造函数

    lateinit var viewModel: PlayerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.player_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("whatHappened","onViewCreated")

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d("whatHappened","onActivityCreated")
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PlayerViewModel::class.java)
        viewModel.apply {
            videoID = position
            VideoPath?.let {
                videoPath = VideoPath
                loadVideo()
            }
            progressBarVisibility.observe(viewLifecycleOwner, {
                Log.d("whatHappenedToSeekBar","${myMedia.duration}")
                progressBar.visibility = it
                seekBar.max = myMedia.duration

            })
            bufferPercent.observe(viewLifecycleOwner, {
                //Log.d("TiaoYiTiao","$it")
                seekBar.secondaryProgress = it * seekBar.max / 100
            })
            video.observe(viewLifecycleOwner){
                Log.d("repository","liveData changed")
                likedNumber.setText("${it.liked}")
            }
        }
        updateProgress()
        surfaceView.holder.addCallback(object :SurfaceHolder.Callback{
            override fun surfaceCreated(holder: SurfaceHolder) {
                Log.d("TiaoYiTiao","surfaceCreated")
                viewModel.surfaceViewStatus = true
                viewModel.myMedia.setDisplay(holder)
                if(viewModel.presentStatus){
                    viewModel.myMedia.start()
                }
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {/*
                Log.d("TiaoYiTiao","surfaceChanged")
                viewModel.myMedia.setDisplay(holder)
                Log.d("TiaoYiTiao","${viewModel.presentStatus}")
                if(viewModel.presentStatus){
                    lifecycleScope.launch {
                        while (!viewModel.myMedia.isPlaying){
                            Log.d("TiaoYiTiao","start in surfaceView")
                            viewModel.myMedia.start()
                            delay(100)
                        }
                    }
                }
                */

            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                Log.d("TiaoYiTiao","surfaceDestroyed")
                viewModel.surfaceViewStatus = false
            }

        })
        seekBar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser){
                    viewModel.playerSeekToProgress(progress)
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
        likedImage.setOnClickListener{
            likedNumberMotionLayout.progress = 0.1f
            likedNumberMotionLayout.transitionToEnd()
            /*val video = Video()
            video.liked = viewModel.video.value!!.liked + 1
             */
            val video = viewModel.video.value
            video!!.liked = viewModel.video.value!!.liked + 1
            viewModel.video.value = video
            viewModel.updateVideo(video)
            Log.d("repository","now is ${viewModel.video.value!!.liked}")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("whatHappened","onDestroyView")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d("whatHappened","onDestroy")
    }

    override fun onStop() {
        super.onStop()
        viewModel.presentStatus = false
        Log.d("whatHappened","onStop")
    }
    override fun onResume() {
        super.onResume()
        Log.d("TiaoYiTiao","onResume start")
        viewModel.presentStatus = true
        if(viewModel.surfaceViewStatus&&viewModel.presentStatus){
            viewModel.myMedia.start()
        }
        /*if(!viewModel.myMedia.isPlaying){
            Log.d("TiaoYiTiao","onResume start")
            viewModel.myMedia.start()
        }
         */
        /*守护线程，确保其一定执行
         lifecycleScope.launch {
            while (!viewModel.myMedia.isPlaying){
                    viewModel.myMedia.start()
                delay(500)
            }
        }
         */

    }
    override fun onPause() {
        super.onPause()
        viewModel.presentStatus = false
        Log.d("TiaoYiTiao","onPause pause")
        viewModel.myMedia.pause()
    }



    private fun updateProgress(){
        lifecycleScope.launch {
            while (true){
                delay(500)
                seekBar.progress = viewModel.myMedia.currentPosition
            }
        }
    }

}