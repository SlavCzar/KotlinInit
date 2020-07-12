package com.example.myapplication.paging

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.myapplication.models.News
import com.example.myapplication.models.TopHeadlines
import com.example.myapplication.network.NetworkStateResource
import kotlinx.coroutines.CoroutineScope
import retrofit2.Response

class TopHeadlinesFactory( private val searchQuery:String?= null,
                           val category: String? = null,
                           val scope: CoroutineScope? = null,
                           var networkStateIndicator : MutableLiveData<NetworkStateResource<Response<TopHeadlines>>> = MutableLiveData()
) : DataSource.Factory<Int,News>(){
    private lateinit var dataSource: TopHeadlinesDataSource


    override fun create(): DataSource<Int, News> {
        Log.d("DataSourceFactory","Fetching Data from datasource for category : $category")
        dataSource =
            scope?.let {
                TopHeadlinesDataSource(
                    searchQuery = searchQuery, category = category, scope = it,
                    networkStateIndicator = networkStateIndicator
                )
            }!!
        return dataSource
    }
}