package com.example.myapplication.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myapplication.models.News
import com.example.myapplication.models.SourceX

@Database(entities = [News::class, SourceX::class],version = 1,exportSchema = true)
@TypeConverters(SourceConverter::class)
public abstract class NewsDatabase: RoomDatabase() {

    private var newContext: Context? = null
    abstract fun newsDao(): NewsDao?
    abstract fun sourcesDao(): SourcesDao?
    private val TAG = "NewsDatabase"


    companion object {
        private var sInstance: NewsDatabase? = null
        fun getInstance(context: Context): NewsDatabase {
            if (sInstance == null) {

                sInstance = Room.databaseBuilder(context.applicationContext,
                    NewsDatabase::class.java, "NEWS_DATABASE.db").fallbackToDestructiveMigration().build()
            }
            return sInstance as NewsDatabase
        }
    }
}