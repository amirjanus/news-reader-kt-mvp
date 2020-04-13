package com.example.newsreader

import android.app.Application
import com.example.newsreader.di.appModule
import com.example.newsreader.view.article.articleActivityModule
import com.example.newsreader.view.main.mainActivityModule
import io.realm.Realm
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class NewsReaderApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)

        startKoin {
            androidLogger()
            androidContext(this@NewsReaderApplication)
            modules(appModule, mainActivityModule, articleActivityModule)
        }
    }

}