package com.bamboo.doushou.datarepository

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


@Entity//不要头铁写成数据类了
class Video{

    @PrimaryKey(autoGenerate = true)  var ID = 0
    @ColumnInfo(name = "liked_number") var liked:Int = 0
    @ColumnInfo(name = "path") var path:String? = null
    @ColumnInfo(name = "image_path") var imagePath:String? = null
    @ColumnInfo(name = "label") var label:String? = null
    @ColumnInfo(name = "selection") var selection:Boolean? = true
    @ColumnInfo(name = "time") var time:String? = null
    @ColumnInfo(name = "title") var title:String? = null
}