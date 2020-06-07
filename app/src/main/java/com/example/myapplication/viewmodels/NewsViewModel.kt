package com.example.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.myapplication.models.News
import com.example.myapplication.models.TopHeadlines
import com.example.myapplication.repositories.NewsRepository
import retrofit2.Call
import retrofit2.http.Query

public class NewsViewModel(application : Application) : AndroidViewModel(application) {

    val newsList : LiveData<List<News>>
    private val newsRepository = NewsRepository(application)

    init {
       this.newsList = newsRepository.newsList
    }


    public fun getAllHeadlines(searchQuery:String?=null, country : String?=null,
                                source: String?=null,languague: String?=null) {
        return newsRepository.getAllNews(searchQuery = searchQuery,country = country);
    }
}