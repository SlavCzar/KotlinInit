package com.example.myapplication.network

import com.example.myapplication.models.SourcesResponse
import com.example.myapplication.models.TopHeadlines
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


const val BASE_URL = "https://newsapi.org/v2/"

interface ApiService {

//    @Headers("x-api-key:8a842c0abbae4a2caae55feb66c9dd77")
    @GET("top-headlines")
    fun getTopHeadlines(
    @Query("q") searchQuery: String? = null,
    @Query("country") country: String? = null,
    @Query("sources") source: String? = null,
    @Query("apiKey") apiKey: String,
    @Query("category")category: String?=null,
    @Query("page")page:Int = 1,
    @Query("pageSize")pageSize:Int = 30
) : Call<TopHeadlines>

    @GET("everything")
    fun getEverything(@Query("q")searchQuery:String?=null, @Query("language")language : String?=null,
                        @Query("sources")source: String?=null, @Query("apiKey")apiKey: String,
                      @Query("sortBy")sortBy: String = "publishedAt",
                        @Query("page")page: Int,@Query("pageSize")pageSize: Int) : Call<TopHeadlines>

    @GET("sources")
    fun getAllSourcesFromAPI(@Query("apiKey")apiKey: String) : Call<SourcesResponse>

}