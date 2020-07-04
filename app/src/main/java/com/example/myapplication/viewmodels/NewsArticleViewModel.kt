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
import com.example.myapplication.adapters.TopHeadlinesDataSource
import com.example.myapplication.models.News
import com.example.myapplication.models.TopHeadlines
import com.example.myapplication.network.NetworkStateResource
import com.example.myapplication.network.SearchEverythingDataSource
import com.example.myapplication.repositories.NewsArticleRepository
import retrofit2.Response

public class NewsArticleViewModel(application : Application) : AndroidViewModel(application) {

    val newsList : LiveData<List<News>>
    var headlinesPagedList  :LiveData<PagedList<News>>
    var searchQueryLiveData = MutableLiveData<String>()
    var categoryLiveData = MutableLiveData<String>()
    var categoricalHeadlinesList :LiveData<PagedList<News>>
    var savedHeadlinesList :LiveData<PagedList<News>>
    var networkStateIndicator : MutableLiveData<NetworkStateResource<Response<TopHeadlines>>> = MutableLiveData()
    val config : PagedList.Config
    private val newsRepository = NewsArticleRepository(application)

    init {
        //start loading the data
        networkStateIndicator.postValue(NetworkStateResource.Loading())
        config = PagedList.Config.Builder().setEnablePlaceholders(false)
            .setPageSize(PAGE_SIZE)
            .build()
        headlinesPagedList = Transformations.switchMap(searchQueryLiveData,object :Function<String,LiveData<PagedList<News>>>{
            override fun apply(input: String?): LiveData<PagedList<News>> {
                if (input == null || input == "" || input == "%%")
                    return topHeadlinesPagedListBuilder(
                        searchQuery = null,
                        category = null,
                        config = config,
                        networkStateIndicator = networkStateIndicator
                    ).build()
                else
                    return searchEverythingPagedListBuilder(
                        searchQuery = input,
                        config = config,
                        networkStateIndicator = networkStateIndicator
                    ).build()
            }
        })
        categoryLiveData.postValue("general")

        categoricalHeadlinesList = Transformations.switchMap(categoryLiveData,object :Function<String,LiveData<PagedList<News>>>{
            override fun apply(input: String?): LiveData<PagedList<News>> {
                if(input!=null) {
                    d("switchmap",input)
                }
                if (input == null || input == "" || input == "%%")
                    return topHeadlinesPagedListBuilder(searchQuery = null ,category = null,config = config,networkStateIndicator = networkStateIndicator).build()
                else
                    return topHeadlinesPagedListBuilder(
                        category = input,
                        config = config,
                        networkStateIndicator = networkStateIndicator
                    ).build()
            }
        })

        savedHeadlinesList = LivePagedListBuilder<Int,News>(newsRepository.getSavedNews() as DataSource.Factory<Int, News>, PAGE_SIZE).build()
    }
    private fun topHeadlinesPagedListBuilder(
        searchQuery: String? = null,
        category: String? = null,
        config: PagedList.Config?,
        networkStateIndicator: MutableLiveData<NetworkStateResource<Response<TopHeadlines>>>
    ): LivePagedListBuilder<Int,News> {
        val dataSourceFactory = object : DataSource.Factory<Int,News>(){
            override fun create(): DataSource<Int, News> {
                Log.d("ViewModel","Fetching Data from datasource")
                return TopHeadlinesDataSource(searchQuery,category,viewModelScope,networkStateIndicator)
            }
        }
        return LivePagedListBuilder<Int,News>(dataSourceFactory, config!!)
    }
    private fun searchEverythingPagedListBuilder(
        searchQuery: String? = null,
        category: String? = null,
        config: PagedList.Config?,
        networkStateIndicator: MutableLiveData<NetworkStateResource<Response<TopHeadlines>>>
    ): LivePagedListBuilder<Int,News> {
        val dataSourceFactory = object : DataSource.Factory<Int,News>(){
            override fun create(): DataSource<Int, News> {
                Log.d("ViewModel","Fetching Data from datasource")
                return SearchEverythingDataSource(searchQuery,viewModelScope,networkStateIndicator)
            }
        }
        return LivePagedListBuilder<Int,News>(dataSourceFactory, config!!)
    }




    init {
       this.newsList = newsRepository.newsList
    }


    public fun getAllHeadlines(
        searchQuery: String? = null, country: String? = null,
        source: String? = null,
        category: String?=null)
    {
        return newsRepository.getAllNews(searchQuery = searchQuery,country = country,source = source,category = category);
    }

    public fun insertNewsInDb(news: News)
    {
        d("viewmodel", "insertNewsInDb: ")
        newsRepository.insertNews(news)
    }

    public fun deleteNewsFromDb(news: News)
    {
        d("viewmodel", "insertNewsInDb: ")
        newsRepository.deleteNews(news)
    }


}