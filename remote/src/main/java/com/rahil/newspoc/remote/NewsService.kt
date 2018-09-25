package com.rahil.newspoc.remote

import io.reactivex.Flowable
import com.rahil.newspoc.remote.model.NewsModel
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Defines the abstract methods used for interacting with the News API
 */
interface NewsService {

    @GET("top-headlines")
    fun getNews(@Query("sources") source:String, @Query("apiKey") apiKey:String): Flowable<NewsApiResponse>

    class NewsApiResponse {
        lateinit var articles: List<NewsModel>
    }

}