package com.example.newsreader.data.remote.retrofit

import android.content.res.Resources
import com.example.newsreader.R
import com.example.newsreader.data.remote.newsapi.NewsApi
import com.example.newsreader.utils.models.NewsSource
import io.reactivex.Single
import retrofit2.Response
import java.util.*

class RetrofitNewsApi(newsApiService: NewsApiService, resources: Resources) : NewsApi {

    private val mService: NewsApiService = newsApiService

    private val mApiKey: String = resources.getString(R.string.news_api_key)

    /**
     * Get news articles from NewsApi.
     *
     * @param source Article source.
     * @param sortBy How to sort articles.
     * @return NewsSourceNames object with timestamp and articles list.
     */
    override fun getNews(source: String, sortBy: String): Single<NewsSource> {
        return Single.fromCallable({ mService.getNews(source, sortBy, mApiKey).execute() })
            .map({ response: Response<NewsSource> -> toNewsSource(response, source) })
    }

    /**
     * Get NewsData from retrofit response.
     *
     * @param response Retrofit response.
     * @param newsSourceName Article source.
     * @return NewsData object from Retrofit response.
     */
    private fun toNewsSource(response: Response<NewsSource>, newsSourceName: String): NewsSource {
        val newsSource: NewsSource = response.body() ?: NewsSource()

        newsSource.date = Calendar.getInstance(TimeZone.getTimeZone("UTC")).time
        newsSource.id = newsSourceName

        return newsSource
    }

}
