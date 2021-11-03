package com.bamboo.doushou

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bamboo.doushou.utils.SaveImageKotlin
import com.bamboo.doushou.datarepository.Video
import com.bamboo.doushou.fragmrnt.CHOOSE_PHOTO
import com.bamboo.doushou.fragmrnt.REQUEST_WRITE_EXTERNAL_STORAGE
import kotlinx.android.synthetic.main.activity_add_video.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*


class AddVideo : AppCompatActivity() {
    private lateinit var addVideoViewModel: AddVideoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_video)
        addVideoViewModel = ViewModelProvider(this).get(AddVideoViewModel::class.java)
        floatingActionButton2.setOnClickListener{
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED)
            {
                Log.d("funny","funny")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        REQUEST_WRITE_EXTERNAL_STORAGE)
                }
            }
            else{
                recordVideo()
            }

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_WRITE_EXTERNAL_STORAGE->{
                if(grantResults.isNotEmpty()&&grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    recordVideo()
                }
                else{
                    Toast.makeText(this,"权限请求失败",Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    private fun recordVideo(){
        intent = Intent(Intent.ACTION_PICK) //对的
        //intent = Intent(Intent.ACTION_GET_CONTENT)//WRONG
        intent.type = "video/*"
        startActivityForResult(intent, CHOOSE_PHOTO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            CHOOSE_PHOTO->{
                if(resultCode == RESULT_OK){
                    data?.let {
                        val uri = data.data
                        val videoPath = getRealFilePath(uri!!)
                        Log.d("videoTest",videoPath)
                        val mediaMetadataRetriever = MediaMetadataRetriever()
                        mediaMetadataRetriever.setDataSource(videoPath)
                        val bitmap = mediaMetadataRetriever.frameAtTime
                        imageView.setImageBitmap(bitmap)
                        bitmap?.let {
                            val imagePath = SaveImageKotlin.saveImages(applicationContext,bitmap)
                            val video = Video()
                            video.apply {
                                path = videoPath
                                this.imagePath = imagePath
                                label = "default"
                                time = getTime()
                                title = "default"
                            }
                            addVideoViewModel.insertVideos(video)
                            /*lifecycleScope.launch{
                                val imagePath = saveImages(bitmap)
                                //val imagePath = "content:/media/external/images/media/79"
                                imagePath?.let {
                                    val video = Video()
                                    addVideoViewModel.insertVideos(video)
                                    Log.d("dataBaseeee",video.toString())
                                }
                            }

                             */
                        }
                    }
                }
            }
        }
    }
    private fun getRealFilePath(uri: Uri):String{
        var path = "test"
        val prj = arrayOf(MediaStore.Video.Media.DATA)
        val cursor = contentResolver.query(uri,prj,null,null,null)
        cursor?.let {
            if(it.moveToFirst()){
                path = it.getString(it.getColumnIndex(MediaStore.Images.Media.DATA))
            }
            it.close()
        }
        return  path
    }
    private suspend fun saveImages(bitmap: Bitmap):String?{
        return withContext(Dispatchers.IO){
            val saveUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                ContentValues()
            )?: kotlin.run {
                MainScope().launch { Toast.makeText(this@AddVideo,"保存图片失败",Toast.LENGTH_LONG).show() }
                return@withContext null
            }
            contentResolver.openOutputStream(saveUri).use {
                if(bitmap.compress(Bitmap.CompressFormat.JPEG,90,it)){
                    MainScope().launch { Toast.makeText(this@AddVideo,"保存图片成功",Toast.LENGTH_LONG).show() }
                    Log.d("dataBaseeee", saveUri.toString())
                    return@withContext saveUri.toString()
                }else{
                    MainScope().launch { Toast.makeText(this@AddVideo,"保存图片失败",Toast.LENGTH_LONG).show() }
                    return@withContext null
                }

            }
        }
    }
    fun getTime():String{
        val format = SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss")
        return format.format(Date())
    }
}