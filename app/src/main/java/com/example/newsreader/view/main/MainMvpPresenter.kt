package com.example.newsreader.view.main

import com.example.newsreader.view.base.BaseMvpPresenter

interface MainMvpPresenter : BaseMvpPresenter {

    /**
     * Called when user clicks on item in Article RecyclerView.
     *
     * @param articleIndex Position index of selected article item.
     */
    fun articleSelected(articleIndex: Int)

}
