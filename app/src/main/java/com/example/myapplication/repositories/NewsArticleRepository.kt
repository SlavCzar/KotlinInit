package com.example.myapplication.repositories

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.myapplication.network.ApiService
import com.example.myapplication.db.NewsDatabase
import com.example.myapplication.models.News
import com.example.myapplication.models.TopHeadlines
import com.example.myapplication.network.toSimpleCall
import com.example.myapplication.utils.Constants.Companion.API_KEY
import com.example.myapplication.utils.Constants.Companion.BASE_URL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

private const val TAG = "NewsRepository"
class NewsArticleRepository(val application: Application) {

    val mIoExecutor: ExecutorService = Executors.newSingleThreadExecutor()
//    val newsDatabase : NewsDatabase = NewsDatabase.getInstance(application)
    val newsList = MutableLiveData<List<News>>()
    val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiService = retrofit.create(ApiService::class.java)
    val database = NewsDatabase.getInstance(application)


//    fun getAllNews(searchQuery: String? = null,country: String? = null,
//        sortBy: String? = null,source: String? = null,category: String?=null)
//    {
//        apiService.getTopHeadlines(apiKey = API_KEY,searchQuery = searchQuery
//        ,country = country,source = source,category = category).enqueue(object : Callback<TopHeadlines>{
//
//            override fun onResponse(call: Call<TopHeadlines>, response: Response<TopHeadlines>) {
//                Log.d("Repository","getting top headlines")
//                Log.d(TAG, "onResponse: "+ response.body().toString())
//                newsList.value = response.body()?.articles
////                newsList.value?.let { insertNews(it) }
//
//            }
//
//            override fun onFailure(call: Call<TopHeadlines>, t: Throwable) {
//                Log.e("Repository","ERROR getting top headlines")
//                Toast.makeText(application,t.toString(),Toast.LENGTH_LONG).show();
//                Log.e("ApiCallError",t.toString())
//            }
//        })
//    }

    fun getSavedNews(): DataSource.Factory<Int, News>?
    {
        return database.newsDao()?.getSavedNews()
    }
    fun insertNews(news: News)
    {
        mIoExecutor.execute {
            Log.d(TAG, "insertNews: ")
                database.newsDao()?.insertNews(news)
            }
    }

    fun deleteNews(news: News) {
        mIoExecutor.execute {
            Log.d(TAG, "deleteNews: ")
            database.newsDao()?.deleteNews(news)
        }
    }
}                                                                                                                                                                                                                                           