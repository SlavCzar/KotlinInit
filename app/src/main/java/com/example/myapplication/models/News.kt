package com.example.myapplication.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "news_table",primaryKeys = ["title","source"])
data class News (

    @ColumnInfo(defaultValue = "Null title")
    val title : String,

    @ColumnInfo(defaultValue = "Null desc")
    val description : String,

    @ColumnInfo(defaultValue = "Null publishedAt")
    val publishedAt : String,

    @ColumnInfo(defaultValue = "Null Content")
    val content : String,

    @ColumnInfo(defaultValue = "Null urlToImage")
    val urlToImage : String,

    @ColumnInfo(defaultValue = "Null articleUrl")
    val url : String,

    @ColumnInfo
    val source : Source,

    @ColumnInfo(defaultValue = "Null author")
    val author : String,

    @ColumnInfo
    @Expose(serialize = false,deserialize = false)
    var isSaved:Int = 0
)