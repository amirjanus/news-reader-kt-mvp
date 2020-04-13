package com.example.newsreader.view.main

import com.example.newsreader.view.main.adapter.RecyclerArticleAdapter
import org.koin.core.qualifier.named
import org.koin.dsl.module

val mainActivityModule = module {

    scope(named<MainActivity>()) {
        factory { RecyclerArticleAdapter() }

        factory<MainMvpPresenter> { (view: MainMvpView) -> MainPresenter(view, get()) }
    }

}
