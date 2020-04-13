package com.example.newsreader.data.local.realm.models

import com.example.newsreader.utils.models.Article
import com.example.newsreader.utils.models.NewsSource
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

/**
 * Class for saving news data to Realm database.
 */
open class RmNewsSource() : RealmObject() {

    @PrimaryKey
    var id: String = ""

    /* Timestamp showing when was http response received. */
    var date: Date = Date()

    /* List of articles. */
    var articles: RealmList<RmArticle> = RealmList()

    constructor(newsSource: NewsSource): this() {
        id = newsSource.id
        date = newsSource.date

        for (article: Article in newsSource.articles)
            articles.add( RmArticle(article))
    }

    /**
     * Returns Articles list.
     *
     * @return Articles list.
     */
    fun getArticleList(): List<Article> {
        val articleList: MutableList<Article> = mutableListOf()

        for (rmArticle: RmArticle in articles)
            articleList.add(rmArticle.toArticle())

        return articleList
    }

}
