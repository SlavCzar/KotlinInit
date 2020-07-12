package com.example.myapplication.network

import com.example.myapplication.models.SourcesResponse
import com.example.myapplication.models.TopHeadlines
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


const val BASE_URL = "https://newsapi.org/v2/"

interface ApiService {


    /**
     * This function gets a limited number of top breaking news headlines for the given params
     *
     *
     * @param searchQuery : A query for which the latest news is to be fetched
     * @param country : The 2-letter ISO 3166-1 code of the country you want to get top-headlines for
     * @param sources : A comma separated string of specific news sources to get headlines from e.g "bbc-sport, abc-news"
     * @param category : A string specifying the category of headlines: "science,sport,technology,business,health,entertainment,general"
     * @param page : Used to page through the results using the paging library
     * @param pageSize : used to specify the number of results per page (default: 20,  max: 100)
     */
    @GET("top-headlines")
    fun getTopHeadlines(
        @Query("q") searchQuery: String? = null,
        @Query("country") country: String? = null,
        @Query("sources") sources: String? = null,
        @Query("apiKey") apiKey: String,
        @Query("category")category: String?=null,
        @Query("page")page:Int = 1,
        @Query("pageSize")pageSize:Int = 30
) : Call<TopHeadlines>

    /** This function is used to get all possible news headlines of all time for a given query
     * (For params common with /top-headlines, refer the getTopHeadlines function )
     *
     *
     * @param language : The 2-letter ISO-639-1 code of the language you want to get headlines for (e.g : en, de ,fr)
     * @param sortBy : The order to sort the articles in. Possible options: relevancy, popularity, publishedAt.
     *                  relevancy = articles more closely related to q come first.
     *                   popularity = articles from popular sources and publishers come first.
     *                   publishedAt = newest articles come first.
     *                   Default: publishedAt
     *
     */
    @GET("everything")
    fun getEverything(@Query("q")searchQuery:String?=null, @Query("language")language : String?=null,
                        @Query("sources")source: String?=null, @Query("apiKey")apiKey: String,
                      @Query("sortBy")sortBy: String = "publishedAt",
                        @Query("page")page: Int,@Query("pageSize")pageSize: Int) : Call<TopHeadlines>

    /**
     * This function gets all the possible news sources available with newsapi.org
     */
    @GET("sources")
    fun getAllSourcesFromAPI(@Query("apiKey")apiKey: String) : Call<SourcesResponse>

}