package com.example.myapplication.repositories

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.ApiService
import com.example.myapplication.BASE_URL
import com.example.myapplication.dao.NewsDao
import com.example.myapplication.dao.NewsDatabase
import com.example.myapplication.models.News
import com.example.myapplication.models.TopHeadlines
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

private const val TAG = "NewsRepository"
class NewsRepository(val application: Application) {

    val mIoExecutor: ExecutorService = Executors.newSingleThreadExecutor()
//    val newsDatabase : NewsDatabase = NewsDatabase.getInstance(application)
    val newsList = MutableLiveData<List<News>>()
    val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiService = retrofit.create(ApiService::class.java)

    fun getAllNews(
        searchQuery: String? = null,
        country: String? = null,
        sortBy: String? = null,
        source: String? = null,
        category: String?=null
    ){
        apiService.getTopHeadlines(apiKey = "8a842c0abbae4a2caae55feb66c9dd77",searchQuery = searchQuery
        ,country = country,source = source,category = category).enqueue(object : Callback<TopHeadlines>{

            override fun onResponse(call: Call<TopHeadlines>, response: Response<TopHeadlines>) {
                Log.d("Repository","getting top headlines")
                Log.d(TAG, "onResponse: "+ response.body().toString())
                newsList.value = response.body()?.articles
            }

            override fun onFailure(call: Call<TopHeadlines>, t: Throwable) {
                Log.e("Repository","ERROR getting top headlines")
                Toast.makeText(application,t.toString(),Toast.LENGTH_LONG).show();
                Log.e("ApiCallError",t.toString())
            }


        })

    }


}                                                                                                                                                                                                                                           