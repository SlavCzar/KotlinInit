package com.example.myapplication.viewmodels

import android.app.Application
import android.nfc.tech.MifareUltralight.PAGE_SIZE
import android.util.Log
import android.util.Log.d
import androidx.arch.core.util.Function
import androidx.lifecycle.*
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.myapplication.adapters.NewsDataSource
import com.example.myapplication.models.News
import com.example.myapplication.repositories.NewsRepository

public class NewsArticleViewModel(application : Application) : AndroidViewModel(application) {

    val newsList : LiveData<List<News>>
    var headlinesPagedList  :LiveData<PagedList<News>>
    var searchQueryLiveData = MutableLiveData<String>()
    var categoryLiveData = MutableLiveData<String>()
    var categoricalHeadlinesList :LiveData<PagedList<News>>
    val config : PagedList.Config

    init {
        config = PagedList.Config.Builder().setEnablePlaceholders(false)
            .setPageSize(PAGE_SIZE)
            .build()
        headlinesPagedList = Transformations.switchMap(searchQueryLiveData,object :Function<String,LiveData<PagedList<News>>>{
            override fun apply(input: String?): LiveData<PagedList<News>> {
                if (input == null || input == "" || input == "%%")
                    return initialisePagedListBuilder(searchQuery = null ,category = null,config = config).build()
                else
                    return initialisePagedListBuilder(searchQuery = input,category = null,config = config).build()
            }
        })
        categoryLiveData.postValue("general")

        categoricalHeadlinesList = Transformations.switchMap(categoryLiveData,object :Function<String,LiveData<PagedList<News>>>{
            override fun apply(input: String?): LiveData<PagedList<News>> {
                if(input!=null) {
                    d("switchmap",input)
                }
                if (input == null || input == "" || input == "%%")
                    return initialisePagedListBuilder(searchQuery = null ,category = null,config = config).build()
                else
                    return initialisePagedListBuilder(category = input,config = config).build()
            }
        })
    }
    private fun initialisePagedListBuilder(searchQuery: String? = null,category: String? = null,config: PagedList.Config?): LivePagedListBuilder<Int,News> {
        val dataSourceFactory = object : DataSource.Factory<Int,News>(){
            override fun create(): DataSource<Int, News> {
                Log.d("ViewModel","Fetching Data from datasource")
                return NewsDataSource(searchQuery,category,viewModelScope)
            }
        }
        return LivePagedListBuilder<Int,News>(dataSourceFactory, config!!)
    }



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