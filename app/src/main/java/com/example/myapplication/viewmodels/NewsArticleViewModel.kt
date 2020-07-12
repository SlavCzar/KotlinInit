package com.example.myapplication.viewmodels

import android.app.Application
import android.nfc.tech.MifareUltralight.PAGE_SIZE
import android.util.Log.d
import androidx.arch.core.util.Function
import androidx.lifecycle.*
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.myapplication.paging.SearchEverythingFactory
import com.example.myapplication.paging.TopHeadlinesDataSource
import com.example.myapplication.paging.TopHeadlinesFactory
import com.example.myapplication.models.News
import com.example.myapplication.models.TopHeadlines
import com.example.myapplication.network.NetworkStateResource
import com.example.myapplication.repositories.NewsArticleRepository
import retrofit2.Response

public class NewsArticleViewModel(application : Application) : AndroidViewModel(application) {

    var headlinesPagedList  :LiveData<PagedList<News>>
    var searchQueryLiveData = MutableLiveData<String>()
    var categoryLiveData = MutableLiveData<String>()
    var categoricalHeadlinesList :LiveData<PagedList<News>>
    var savedHeadlinesList :LiveData<PagedList<News>>
    var networkStateIndicator : MutableLiveData<NetworkStateResource<Response<TopHeadlines>>> = MutableLiveData()
    val config : PagedList.Config
    var dataSourceLiveData: MutableLiveData<TopHeadlinesDataSource> = MutableLiveData<TopHeadlinesDataSource>()
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
        categoryLiveData.postValue("sports")

        categoricalHeadlinesList = Transformations.switchMap(categoryLiveData,object :Function<String,LiveData<PagedList<News>>>{
            override fun apply(input: String?): LiveData<PagedList<News>> {
                if(input!=null) {
                    d("switchmap",input)
                }
                if (input == null || input == "")
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
    private fun topHeadlinesPagedListBuilder(searchQuery: String? = null, category: String? = null,
        config: PagedList.Config?,networkStateIndicator: MutableLiveData<NetworkStateResource<Response<TopHeadlines>>>
    ): LivePagedListBuilder<Int,News> {
        val factory = TopHeadlinesFactory(
            searchQuery = searchQuery, scope = viewModelScope, category = category,
            networkStateIndicator = networkStateIndicator
        )
        return LivePagedListBuilder<Int,News>(factory,config!!)
    }
    private fun searchEverythingPagedListBuilder(
        searchQuery: String? = null,
        category: String? = null,
        config: PagedList.Config?,
        networkStateIndicator: MutableLiveData<NetworkStateResource<Response<TopHeadlines>>>
    ): LivePagedListBuilder<Int,News> {
        val dataSourceFactory =
            SearchEverythingFactory(
                searchQuery = searchQuery, scope = viewModelScope,
                networkStateIndicator = networkStateIndicator
            )
        return LivePagedListBuilder<Int,News>(dataSourceFactory, config!!)
    }
    fun refreshData()
    {
        dataSourceLiveData.value?.invalidate()
    }

//    public fun getAllHeadlines(
//        searchQuery: String? = null, country: String? = null,
//        source: String? = null,
//        category: String?=null)
//    {
//        return newsRepository.getAllNews(searchQuery = searchQuery,country = country,source = source,category = category);
//    }

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