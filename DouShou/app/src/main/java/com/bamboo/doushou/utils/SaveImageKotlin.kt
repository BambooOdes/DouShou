package com.bamboo.doushou.utils

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import java.io.File
import java.io.FileOutputStream


object SaveImageKotlin{
    fun saveImages(context: Context,bitmap: Bitmap):String{
        Log.d("SaveImage","start here")
        val file = File("${context.getExternalFilesDir("pics")}")
        Log.d("SaveImage", file.absolutePath)

        if(!file.exists()){
            Log.d("SaveImage","here+")
            if(file.mkdirs()){
                Log.d("SaveImage","success create directory")
            }else{
                Log.d("SaveImage","fail to create directory")
            }
        }else{
            Log.d("SaveImage","file exists")
        }
        val name = "${System.currentTimeMillis()}.jpg"
        val pic = File(file,name)
        Log.d("SaveImage","$pic")
        val fos = FileOutputStream(pic)
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos)
        fos.flush()
        fos.close()
        return pic.absolutePath
    }
}