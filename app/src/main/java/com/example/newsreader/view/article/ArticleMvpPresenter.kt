package com.example.newsreader.view.article

import com.example.newsreader.view.base.BaseMvpPresenter

interface ArticleMvpPresenter : BaseMvpPresenter {

    /**
     * Called when new page is set in ViewPager.
     *
     * @param index Position index of the new ViewPager page.
     */
    fun articlePageChanged(index: Int)

    /**
     * Get articles from database.
     *
     * @param newsSource   News source ID.
     * @param articleIndex Position index of current ViewPager page.
     */
    fun fetchArticles(articleIndex: Int)

}
