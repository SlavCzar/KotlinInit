package com.example.myapplication.adapters

import android.util.Log
import android.util.Log.d
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.myapplication.network.ApiService
import com.example.myapplication.network.BASE_URL
import com.example.myapplication.models.News
import com.example.myapplication.models.TopHeadlines
import com.example.myapplication.network.NetworkStateResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val FIRST_PAGE:Int = 1
private const val PAGE_SIZE = 30;
private const val TAG = "TopHeadlinesDataSource"
public class TopHeadlinesDataSource(private val searchQuery: String?=null, val category: String?=null, private val scope: CoroutineScope,
                                    private val networkStateIndicator: MutableLiveData<NetworkStateResource<Response<TopHeadlines>>>) : PageKeyedDataSource<Int, News>()
{

    val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiService = retrofit.create(ApiService::class.java)

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, News>)
    {
        d(TAG,"Loadinintial called for category : $category")
        scope.launch {

            networkStateIndicator.postValue(NetworkStateResource.Loading())
            apiService.getTopHeadlines(country = "in",apiKey = "8a842c0abbae4a2caae55feb66c9dd77",page = FIRST_PAGE,
                searchQuery = searchQuery,category = category,
                pageSize = PAGE_SIZE).enqueue(object :
                Callback<TopHeadlines>{
                override fun onFailure(call: Call<TopHeadlines>, t: Throwable) {
                    Log.e(TAG, "onFailure: $t")
                    networkStateIndicator.postValue(NetworkStateResource.Error(t.toString()))
                }

                override fun onResponse(call: Call<TopHeadlines>, response: Response<TopHeadlines>) {
                    response.body()?.articles?.let {
                        Log.d("datasource", "onResponse: "+ response.body().toString())
                        callback.onResult(it,null, FIRST_PAGE + 1) }
                    networkStateIndicator.postValue(NetworkStateResource.Success(response))
                }
            })
        }

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, News>)
    {
        d(TAG,"Loadafter called for category : $category")
        scope.launch {
            apiService.getTopHeadlines(country = "in",apiKey = "8a842c0abbae4a2caae55feb66c9dd77",page = params.key,
                searchQuery = searchQuery,category = category,
                pageSize = PAGE_SIZE).enqueue(object : Callback<TopHeadlines>{
                override fun onResponse(call: Call<TopHeadlines>, response: Response<TopHeadlines>) {
                    if(response.isSuccessful)
                    {
                        networkStateIndicator.postValue(NetworkStateResource.Success(response))
                        val nextKey : Int?
                        if(params.key == response.body()?.totalResults)
                            nextKey = null
                        else
                            nextKey = params.key + 1
                        response.body()?.articles?.let { callback.onResult(it, nextKey) };
                    }
                }
                override fun onFailure(call: Call<TopHeadlines>, t: Throwable) {
                    Log.e(TAG, "onFailure: $t" )
                    networkStateIndicator.postValue(NetworkStateResource.Error(t.toString()))
                }
            })
        }

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, News>)
    {
        d(TAG,"Loadbefore called for category : $category")
        scope.launch {
            apiService.getTopHeadlines(country = "in",apiKey = "8a842c0abbae4a2caae55feb66c9dd77",page = params.key,
                searchQuery = searchQuery,category = category,
                pageSize = PAGE_SIZE).enqueue(object :
                Callback<TopHeadlines>{
                override fun onFailure(call: Call<TopHeadlines>, t: Throwable) {
                    Log.e(TAG, "onFailure: $t")
                    networkStateIndicator.postValue(NetworkStateResource.Error(t.toString()))
                }

                override fun onResponse(call: Call<TopHeadlines>, response: Response<TopHeadlines>) {
                    networkStateIndicator.postValue(NetworkStateResource.Success(response))
                    val key:Int?
                    if(params.key>1)
                        key = params.key-1
                    else
                        key = null
                    response.body()?.articles?.let { callback.onResult(it,key) }
                }
            })
        }
    }

}