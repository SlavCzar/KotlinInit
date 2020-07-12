package com.example.myapplication.paging

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.myapplication.models.News
import com.example.myapplication.models.TopHeadlines
import com.example.myapplication.network.NetworkStateResource
import com.example.myapplication.network.RetrofitInstance
import com.example.myapplication.utils.Constants.Companion.API_KEY
import com.example.myapplication.utils.Constants.Companion.FIRST_PAGE
import com.example.myapplication.utils.Constants.Companion.PAGE_SIZE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * This data source is used to actually make network requests to the /everything endpoint
 * and get paged results from the news API
 * @param searchQuery : Query for which articles are to be searched
 * @param scope : CoroutineScope for the calls to run on a background thread
 * @param networkStateIndicator : An object that will notify observers regarding the
 * network call status(Loading, Error or Success)
 */
public class SearchEverythingDataSource(private val searchQuery: String?, private val scope: CoroutineScope,
                                    private val networkStateIndicator: MutableLiveData<NetworkStateResource<Response<TopHeadlines>>>
) : PageKeyedDataSource<Int, News>()
{
    private val TAG = "NewsDataSource"

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, News>)
    {
        scope.launch {
            networkStateIndicator.postValue(NetworkStateResource.Loading())
            RetrofitInstance.api.getEverything(apiKey = API_KEY,page = FIRST_PAGE,
                searchQuery = searchQuery,
                pageSize = PAGE_SIZE
            ).enqueue(object :
                Callback<TopHeadlines> {
                override fun onFailure(call: Call<TopHeadlines>, t: Throwable) {
                    Log.e(TAG, "onFailure: $t")
                    networkStateIndicator.postValue(
                        NetworkStateResource.Error(
                            t.toString()
                        )
                    )
                }

                override fun onResponse(call: Call<TopHeadlines>, response: Response<TopHeadlines>) {
                    response.body()?.articles?.let {
                        Log.d("datasource", "onResponse: "+ response.body().toString())
                        callback.onResult(it,null, FIRST_PAGE + 1) }
                    networkStateIndicator.postValue(NetworkStateResource.Success( response ))
                }
            })
        }

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, News>)
    {
        scope.launch {
            RetrofitInstance.api.getEverything(apiKey = API_KEY,page = params.key,
                searchQuery = searchQuery,
                pageSize = PAGE_SIZE
            ).enqueue(object : Callback<TopHeadlines> {
                override fun onResponse(call: Call<TopHeadlines>, response: Response<TopHeadlines>) {
                    if(response.isSuccessful)
                    {
                        networkStateIndicator.postValue(
                            NetworkStateResource.Success(
                                response
                            )
                        )
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
                    networkStateIndicator.postValue(
                        NetworkStateResource.Error(
                            t.toString()
                        )
                    )
                }
            })
        }

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, News>)
    {
        scope.launch {
            RetrofitInstance.api.getEverything(apiKey = API_KEY,page = params.key,
                searchQuery = searchQuery,
                pageSize = PAGE_SIZE
            ).enqueue(object :
                Callback<TopHeadlines> {
                override fun onFailure(call: Call<TopHeadlines>, t: Throwable) {
                    Log.e(TAG, "onFailure: $t")
                    networkStateIndicator.postValue(
                        NetworkStateResource.Error(
                            t.toString()
                        )
                    )
                }

                override fun onResponse(call: Call<TopHeadlines>, response: Response<TopHeadlines>) {
                    networkStateIndicator.postValue(
                        NetworkStateResource.Success(
                            response
                        )
                    )
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

    override fun invalidate() {
        super.invalidate()
        scope.cancel()
    }
}