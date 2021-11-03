package com.bamboo.doushou.fragmrnt

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.bamboo.doushou.R
import com.bamboo.doushou.datarepository.VideoRepository
import kotlinx.android.synthetic.main.video_fragment.*


class VideoFragment : Fragment() {

    private lateinit var viewModel: VideoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.video_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(VideoViewModel::class.java)
        var temp = 0
        viewModel.videoListLiveData.observe(viewLifecycleOwner){
            if(temp!=it.size){
                temp = it.size
                Log.d("repository","refresh")
                videoPager.apply {

                    adapter = object :FragmentStateAdapter(this@VideoFragment){
                        override fun getItemCount() = it.size

                        override fun createFragment(position: Int): Fragment {
                            Log.d("positionss","create position $position")
                            if(viewModel.playerList.size<=position){
                                viewModel.playerList.add(position,PlayerFragment(it[position].path,position))
                            }
                            return viewModel.playerList[position]
                        }

                    }

                    registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                        override fun onPageSelected(position: Int) {
                            super.onPageSelected(position)
                            viewModel.playerList[position].viewModel.presentStatus = true
                            Log.d("TiaoYiTiao","select position $position")//给surfaceView用
                        }
                    })
                    offscreenPageLimit = 3
                }
            }

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}