package com.example.newsreader.data.remote.newsapi

import com.example.newsreader.utils.models.NewsSource
import io.reactivex.Single

/**
 * Interface for fetching data from NewsApi.
 */
interface NewsApi {

    /**
     * Get news articles from NewsApi.
     *
     * @param source Article source.
     * @param sortBy How to sort articles.
     * @return NewsSourceNames object with timestamp and articles list.
     */
    fun getNews(source: String, sortBy: String): Single<NewsSource>

}
