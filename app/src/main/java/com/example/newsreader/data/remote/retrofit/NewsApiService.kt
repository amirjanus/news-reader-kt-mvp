package com.example.newsreader.data.remote.retrofit

import com.example.newsreader.utils.models.NewsSource
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * NewsApi written as Java interface so that it can be used by Retrofit.
 */
interface NewsApiService {

    companion object {
        /* NewsApi base url. */
        const val baseUrl: String = "https://newsapi.org/"

        /* Path for articles. */
        const val path: String = "v1/articles"
    }

    /**
     * Get news articles from NewsApi.
     *
     * @param source Article source.
     * @param sortBy How to sort articles.
     * @param apiKey NewsApi key.
     * @return NewsData object with timestamp and articles list.
     */
    @GET(path)
    fun getNews(
        @Query("source") source: String,
        @Query("sortBy") sortBy: String,
        @Query("apiKey") apiKey: String
    ): Call<NewsSource>

}
