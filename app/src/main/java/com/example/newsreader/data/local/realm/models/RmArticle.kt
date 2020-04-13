package com.example.newsreader.data.local.realm.models

import com.example.newsreader.utils.models.Article
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

/**
 * Class for saving news data to Realm database.
 */
open class RmArticle() : RealmObject() {

    @PrimaryKey
    var id: Long = 0

    /* Article's title. */
    var title: String = ""

    /* Article's text. */
    var description: String = ""

    /* Url to article's image. */
    var urlToImage: String = ""

    constructor(article: Article): this() {
        id = UUID.randomUUID().leastSignificantBits
        title = article.title
        description = article.description
        urlToImage = article.urlToImage
    }

    /**
     * Create new Articles object from this object.
     *
     * @return Article object created from this object.
     */
    fun toArticle(): Article {
        return Article(id, title, description, urlToImage)
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    override fun toString(): String {
        return "ID: $id, title: $title"
    }

}
