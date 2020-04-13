package com.example.newsreader.data.local.realm

import com.example.newsreader.data.local.interfaces.INewsLocalDb
import com.example.newsreader.data.local.realm.models.RmNewsSource
import com.example.newsreader.utils.models.Article
import com.example.newsreader.utils.models.NewsSource
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.where
import java.util.*
import kotlin.collections.ArrayList

class RealmNewsLocalDb(config: RealmConfiguration) : INewsLocalDb {

    private var mConfig: RealmConfiguration = config
    private lateinit var mRealm: Realm

    /**
     * Get articles from database.
     *
     * @param newsSourceId NewsSourceNames ID.
     * @param date         Articles are returned if the are newer than passed Date.
     */
    override fun getArticles(newsSourceId: String, date: Date): List<Article> {
        mRealm = Realm.getInstance(mConfig)

        val rmNewsSource: RmNewsSource? = mRealm.where<RmNewsSource>()
            .equalTo("id", newsSourceId)
            .greaterThanOrEqualTo("date", date)
            .findFirst()

        val articleList: List<Article> = rmNewsSource?.getArticleList() ?: ArrayList(0)

        mRealm.close()

        return articleList
    }

    /**
     * Get articles from database.
     *
     * @param newsSourceId Articles's parent object ID.
     */
    override fun getArticles(newsSourceId: String): List<Article> {
        mRealm = Realm.getInstance(mConfig)

        val rmNewsSource: RmNewsSource? = mRealm.where<RmNewsSource>()
            .equalTo("id", newsSourceId)
            .findFirst()

        val articleList: List<Article> = rmNewsSource?.getArticleList() ?: ArrayList(0)

        mRealm.close()

        return articleList
    }

    /**
     * Inserts articles in database ( or updates if it already exist ).
     *
     * @param newsSource NewsSourceNames data to save in database.
     */
    override fun insertNews(newsSource: NewsSource) {
        val rmNewsSource: RmNewsSource = RmNewsSource(newsSource)

        mRealm = Realm.getInstance(mConfig)

        mRealm.executeTransaction({ realm: Realm -> realm.insertOrUpdate(rmNewsSource) })

        mRealm.close()
    }

}
