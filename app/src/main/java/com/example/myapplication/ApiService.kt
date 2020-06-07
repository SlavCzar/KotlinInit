package com.example.myapplication

import com.example.myapplication.models.News
import com.example.myapplication.models.TopHeadlines
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query


const val BASE_URL = "https://newsapi.org/v2/"

interface ApiService {

//    @Headers("x-api-key:8a842c0abbae4a2caae55feb66c9dd77")
    @GET("top-headlines")
    fun getTopHeadlines(@Query("q")searchQuery:String?=null, @Query("country")country : String?=null,
                        @Query("sources")source: String?=null, @Query("apiKey")apiKey: String) : Call<TopHeadlines>
}