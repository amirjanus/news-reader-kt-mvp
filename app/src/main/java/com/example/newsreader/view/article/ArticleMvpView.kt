package com.example.newsreader.view.article

import com.example.newsreader.utils.models.Article

interface ArticleMvpView {

    /**
     * Display Articles list in view.
     *
     * @param articleList List of Articles to show.
     * @param index       Position index of Article to show as current item.
     */
    fun showArticles(articleList: List<Article>, index: Int)

    /**
     * Show error message to user.
     */
    fun showErrorMessage()

    /**
     * Display title in app's toolbar.
     *
     * @param title String to show in toolbar.
     */
    fun showToolbarTitle(title: String)

}