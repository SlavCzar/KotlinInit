package com.example.myapplication.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.example.myapplication.models.News

@Dao
public interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
   fun insertNews(news : News)

    @Update
    fun updateNews(news : News)

    @Query("SELECT * FROM news_table")
    fun getSavedNews() : DataSource.Factory<Int,News>

    @Delete
    fun deleteNews(currentItem: News)
}