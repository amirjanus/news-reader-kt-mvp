package com.example.newsreader.utils.models

import java.util.*
import kotlin.collections.ArrayList

/**
 * Class used by RealmNewsApi class for holding data from NewsApi.
 */
class NewsSource {

    var id: String = ""

    /* Timestamp showing when was http response received. */
    var date: Date = Date()

    /* List of articles. */
    var articles: List<Article> = ArrayList(0)

}
