package com.example.myapplication.repositories

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.network.ApiService
import com.example.myapplication.models.SourceX
import com.example.myapplication.models.SourcesResponse
import com.example.myapplication.utils.Constants.Companion.API_KEY
import com.example.myapplication.utils.Constants.Companion.BASE_URL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

private const val TAG = "NewsSourceRepository"
class NewsSourceRepository(val application: Application) {

    val mIoExecutor: ExecutorService = Executors.newSingleThreadExecutor()
//    val newsDatabase : NewsDatabase = NewsDatabase.getInstance(application)
    val sourceList =  MutableLiveData<List<SourceX>>()
    var subscribedSourceList =  MutableLiveData<List<SourceX>>()
    val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService = retrofit.create(ApiService::class.java)

    fun getAllSourcesFromApi(){
            apiService.getAllSourcesFromAPI(apiKey = API_KEY)
            .enqueue(object : Callback<SourcesResponse>{
            override fun onResponse(call: Call<SourcesResponse>,response: Response<SourcesResponse>) {

                Log.d(TAG, "onResponse: ${response.body().toString()}")
                sourceList.value = response.body()?.sources
            }

            override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.toString()}" )
            }
        })
    }
//    fun getSubscribedSourcesFromDb(){
//            subscribedSourceList = newsDatabase.sourcesDao()?.getSubscribedSources() as MutableLiveData<List<SourceX>>
//    }
//
//    fun insertSource(sourceX: SourceX) {
//        mIoExecutor.execute {
//            newsDatabase.sourcesDao()?.insertSource(sourceX)
//        }
//
//    }
}