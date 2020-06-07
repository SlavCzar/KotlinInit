package com.example.myapplication.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "news_table")
data class News (
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    @ColumnInfo
    @SerializedName("title")
    val title : String,
    @ColumnInfo
    @SerializedName("description")
    val description : String,
    @ColumnInfo
    @SerializedName("publishedAt")
    val publishedAt : String,
    @ColumnInfo
    @SerializedName("content")
    val newsContent : String,
    @ColumnInfo
    @SerializedName("urlToImage")
    val imageUrl : String,
    @ColumnInfo
    @SerializedName("url")
    val articleUrl : String,
    @ColumnInfo
    @SerializedName("name")
    val sourceName : String,
    @ColumnInfo
    @SerializedName("author")
    val author : String,
    @ColumnInfo
    @SerializedName("source")
    val source : Source

)