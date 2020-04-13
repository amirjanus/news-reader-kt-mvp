package com.example.newsreader.data

import com.example.newsreader.utils.models.Article
import io.reactivex.Single
import java.util.*

interface MainMvpModel {

    /**
     * Returns list of articles from local database.
     *
     * @param newsSourceId Articles's parent object ID.
     * @return Single with a list of articles or empty list if none exist that satisfy search query.
     */
    fun getLocalArticles(newsSourceId: String): Single<List<Article>>

    /**
     * Returns list of articles from local database.
     *
     * @param newsSourceId Articles's parent object ID.
     * @param date       Searches for articles newer than this Date.
     * @return Single with a list of articles or empty list if none exist that satisfy search query.
     */
    fun getLocalArticles(newsSourceId: String, date: Date): Single<List<Article>>

    /**
     * Returns list of articles from remote api.
     *
     * @param newsSourceId News source ID.
     * @param sortBy     Indicates how to sort articles.
     * @return Single with a list of articles.
     */
    fun getRemoteArticles(newsSourceId: String, sortBy: String): Single<List<Article>>

}
