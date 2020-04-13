package com.example.newsreader.view.article

import androidx.fragment.app.FragmentActivity
import com.example.newsreader.view.article.adapter.PagerArticleAdapter
import org.koin.dsl.module

val articleActivityModule = module {

    scope<ArticleActivity> {
        factory { (activity: FragmentActivity) -> PagerArticleAdapter(activity) }

        factory<ArticleMvpPresenter> { (view: ArticleMvpView) -> ArticlePresenter(view, get()) }
    }

}
