package com.example.myapplication.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.models.SourceX

@Dao
interface SourcesDao {

    @Query("SELECT * FROM sources_table")
    public fun getAllSources() : LiveData<List<SourceX>>

    @Query("SELECT *  from sources_table WHERE isSubscribed = 1")
    public fun getSubscribedSources() : LiveData<List<SourceX>>

    @Insert
    public fun insertSource(sourceX: SourceX)

}