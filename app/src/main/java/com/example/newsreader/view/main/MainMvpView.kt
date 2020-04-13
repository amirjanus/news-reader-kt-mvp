package com.example.newsreader.view.main

import com.example.newsreader.utils.models.Article

interface MainMvpView {

    /**
     * Starts ArticleActivity.
     *
     * @param articleIndex Position index of selected article item.
     */
    fun startArticleActivity(articleIndex: Int)

    /**
     * Display Articles list in view.
     *
     * @param articleList List of Articles to show.
     */
    fun showArticles(articleList: List<Article>)

    /**
     * Shows ProgressBar in view.
     *
     * @param show True to show ProgressBar, false to hide it.
     */
    fun showProgressLoader(show: Boolean)

    /**
     * Show error message to user.
     */
    fun showErrorMessage()

}
