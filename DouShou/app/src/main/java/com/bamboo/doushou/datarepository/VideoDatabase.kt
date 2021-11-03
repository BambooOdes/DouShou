package com.bamboo.doushou.datarepository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Video::class],version = 1,exportSchema = false)
abstract class VideoDatabase :RoomDatabase() {
    abstract fun getVideoDao():VideoDao
    companion object{
        @Volatile
        private var INSTANCE:VideoDatabase? = null

        fun getVideoDatabase(context:Context):VideoDatabase{
            val templeInstance = INSTANCE
            if(templeInstance!=null){
                return templeInstance
            }
            else{
                synchronized(this){
                    val instance = Room.databaseBuilder(context.applicationContext,VideoDatabase::class.java,"video_database").build()
                    INSTANCE = instance
                    return instance
                }
            }

        }
    }
}