package com.example.myapplication.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapplication.models.News

@Dao
public interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
   fun insertNews(news : News)

    @Update
    fun updateNews(news : News)

    @Query("SELECT * FROM news_table")
    fun getAllNews(listOfNews : LiveData<List<News>>)

    @Delete
    fun deleteNews(currentItem: News)
}