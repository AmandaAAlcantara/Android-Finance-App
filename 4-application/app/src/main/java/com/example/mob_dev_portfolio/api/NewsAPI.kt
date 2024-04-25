package com.example.mob_dev_portfolio.api
import com.example.mob_dev_portfolio.models.NewsResponse
import com.example.mob_dev_portfolio.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    //for my network request

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode: String = "gb",
        @Query("category")
        category: String = "business",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>
}