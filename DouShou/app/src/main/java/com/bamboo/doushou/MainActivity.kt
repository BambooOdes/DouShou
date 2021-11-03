package com.bamboo.doushou

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.account_icon_layout.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.friend_icon_layout.*
import kotlinx.android.synthetic.main.home_icon_layout.*
import kotlinx.android.synthetic.main.message_icon_layout.*

/*
带改进：视频比例，加载
 */

class MainActivity : AppCompatActivity() {
    private lateinit var navController :NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navigationHost = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment//在onCreate中获得navController
        navController = navigationHost.navController
        val destinationMap = mapOf(
            R.id.accountFragment to accountMotionLayout,//不能给activity_main中include加上id，不然会找不到被include的layout的id
            R.id.friendFragment to friendMotionLayout,
            R.id.messageFragment to messageMontionLayout,
            R.id.homeFragment to homeMotionLayout
        )
        destinationMap.forEach{map->
            map.value.setOnClickListener { navController.navigate(map.key) }
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            navController.popBackStack()
            destinationMap.values.forEach {  it.progress= 0.0f }
            destinationMap[destination.id]?.transitionToEnd()
        }
        addButton.setOnClickListener{
            intent = Intent(this,AddVideo::class.java)
            startActivity(intent)
        }



    }



}