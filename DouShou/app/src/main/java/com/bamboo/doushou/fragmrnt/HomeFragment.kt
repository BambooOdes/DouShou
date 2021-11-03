package com.bamboo.doushou.fragmrnt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.bamboo.doushou.R
import com.bamboo.doushou.datarepository.VideoRepository
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewPager.apply {

            adapter = object : FragmentStateAdapter(this@HomeFragment){
                override fun getItemCount(): Int {
                    return   3
                }

                override fun createFragment(position: Int): Fragment {
                    return when(position){
                        1 -> VideoFragment()//instance
                        else -> TestFragment()
                    }
                }


            }
        }
        TabLayoutMediator(homeTabLayout,homeViewPager){tab:TabLayout.Tab,i:Int ->
            tab.text = when(i){
                1->"视频"
                else->"空白"
            }
        }.attach()

    }

}