package com.example.newsreader.data.local.interfaces

import com.example.newsreader.utils.models.Article
import com.example.newsreader.utils.models.NewsSource
import java.util.*

/**
 * interface for local database ( Room, Realm ... ).
 */
interface INewsLocalDb {

    /**
     * Get articles from database.
     *
     * @param newsSourceId NewsSourceNames ID.
     * @param date         Articles are returned if the are newer than passed Date.
     */
    fun getArticles(newsSourceId: String, date: Date): List<Article>

    /**
     * Get articles from database.
     *
     * @param newsSourceId Articles's parent object ID.
     */
    fun getArticles(newsSourceId: String): List<Article>

    /**
     * Inserts articles in database ( or updates if it already exist ).
     *
     * @param newsSource NewsSourceNames data to save in database.
     */
    fun insertNews(newsSource: NewsSource)

}
