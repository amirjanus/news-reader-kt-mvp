package com.example.newsreader.data.local.realm

import com.example.newsreader.data.local.realm.models.RmArticle
import com.example.newsreader.data.local.realm.models.RmNewsSource
import io.realm.annotations.RealmModule

@RealmModule( classes = [
    RmArticle::class,
    RmNewsSource::class
])
class RmDatabaseModule {}
