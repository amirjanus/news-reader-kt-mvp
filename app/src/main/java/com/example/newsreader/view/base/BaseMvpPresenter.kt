package com.example.newsreader.view.base

interface BaseMvpPresenter {

    /**
     * Called when the view is ready to be used.
     */
    fun ready()

    /**
     * Called when the view should not be used anymore.
     */
    fun stop()
}
