package com.example.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.models.News
import com.example.myapplication.repositories.NewsRepository

public class NewsArticleViewModel(application : Application) : AndroidViewModel(application) {

    val newsList : LiveData<List<News>>
    var searchQuery = MutableLiveData<String>()

    private val newsRepository = NewsRepository(application)

    init {
       this.newsList = newsRepository.newsList
    }


    public fun getAllHeadlines(
        searchQuery: String? = null, country: String? = null,
        source: String? = null,
        category: String?=null
    ) {
        return newsRepository.getAllNews(searchQuery = searchQuery,country = country,source = source,category = category);
    }


}