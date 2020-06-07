package com.example.myapplication.models


import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "sources_table")
data class SourceX(
    @PrimaryKey
    @ColumnInfo
    val id: String,
    @ColumnInfo
    val category: String,
    @ColumnInfo
    val country: String,
    @ColumnInfo
    val description: String,
    @ColumnInfo
    val language: String,
    @ColumnInfo
    val name: String,
    @ColumnInfo
    val url: String,
    @ColumnInfo
    @Expose(serialize = false,deserialize = false)
    val isSubscribed : Boolean

)