package com.example.myapplication.db

import androidx.room.TypeConverter
import com.example.myapplication.models.Source

class SourceConverter {

    @TypeConverter
    fun getSourceNameFromPOJO( source: Source) : String{
        return source.name
    }

    @TypeConverter
    fun getPOJOFromSourceName(sourceName : String) : Source{
        return Source(sourceName,sourceName)
    }
}