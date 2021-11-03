package com.bamboo.doushou.datarepository

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface VideoDao {
    @Insert
    fun insertVideos(vararg video:Video)
    @Delete()
    fun deleteVideos(vararg video: Video)
    @Update
    fun updateVideos(vararg video: Video)
    @Query("SELECT * FROM VIDEO ORDER BY ID DESC")
    fun getAllVideoLiveData():LiveData<List<Video>>
    @Query("SELECT * FROM VIDEO ORDER BY ID DESC")
    fun getAllVideoList():List<Video>
    @Query("DELETE FROM VIDEO")
    fun clearVideos()
    @Query("SELECT * FROM VIDEO WHERE label LIKE :pattern ORDER BY ID DESC ")
    fun getLabelSelectVideo(pattern:String):LiveData<List<Video>>
    @Query("SELECT * FROM VIDEO WHERE ID = :id  ")
    fun getVideoByID(id:Int):List<Video>//返回List

}