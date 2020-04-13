package com.example.newsreader.data

import com.example.newsreader.data.local.interfaces.INewsLocalDb
import com.example.newsreader.data.remote.newsapi.NewsApi
import com.example.newsreader.utils.models.Article
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class MainModel(newsLocalDb: INewsLocalDb, newsApi: NewsApi) : MainMvpModel {

    private val mApi: NewsApi = newsApi
    private val mLocalDb: INewsLocalDb = newsLocalDb

    /**
     * Returns list of articles from local database.
     *
     * @param newsSourceId Articles's parent object ID.
     * @return Single with a list of articles or empty list if none exist that satisfy search query.
     */
    override fun getLocalArticles(newsSourceId: String): Single<List<Article>> {
        return Single.fromCallable({ mLocalDb.getArticles(newsSourceId) })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    /**
     * Returns list of articles from local database.
     *
     * @param newsSourceId Articles's parent object ID.
     * @param date       Searches for articles newer than this Date.
     * @return Single with a list of articles or empty list if none exist that satisfy search query.
     */
    override fun getLocalArticles(newsSourceId: String, date: Date): Single<List<Article>> {
        return Single.fromCallable({ mLocalDb.getArticles(newsSourceId, date) })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    /**
     * Returns list of articles from remote api.
     *
     * @param newsSourceId News source ID.
     * @param sortBy     Indicates how to sort articles.
     * @return Single with a list of articles.
     */
    override fun getRemoteArticles(newsSourceId: String, sortBy: String): Single<List<Article>> {
        return mApi.getNews(newsSourceId, sortBy)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .map({ newsSource -> mLocalDb.insertNews(newsSource) })
            .map({ mLocalDb.getArticles(newsSourceId) })
            .observeOn(AndroidSchedulers.mainThread())
    }

}