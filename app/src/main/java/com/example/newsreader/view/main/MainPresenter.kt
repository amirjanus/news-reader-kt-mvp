package com.example.newsreader.view.main

import com.example.newsreader.data.MainMvpModel
import com.example.newsreader.data.constants.NewsSourceNames
import com.example.newsreader.utils.models.Article
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.*
import kotlin.collections.ArrayList

class MainPresenter(mainMvpView : MainMvpView, mainMvpModel : MainMvpModel) : MainMvpPresenter {

    private val mView : MainMvpView = mainMvpView
    private val mModel : MainMvpModel = mainMvpModel

    private var mDisposable : CompositeDisposable? = null

    /**
     * Called when the view is ready to be used.
     */
    override fun ready() {
        fetchArticles()
    }

    /**
     * Called when the view should not be used anymore.
     */
    override fun stop() {
        dispose()
    }

    /**
     * Called when user clicks on item in Article RecyclerView.
     *
     * @param articleIndex Position index of selected article item.
     */
    override fun articleSelected(articleIndex : Int) = mView.startArticleActivity(articleIndex)

    /**
     * Get articles from database or api.
     */
    private fun fetchArticles() {
        mDisposable = CompositeDisposable()

        // Show loader and clean the view.
        mView.showProgressLoader(true)
        mView.showArticles(ArrayList(0))

        // Try to get news data from ( local ) database.
        val disposable : Disposable = mModel.getLocalArticles(NewsSourceNames.BBC.getString(), getDate())
            .subscribe({ articleList: List<Article> -> onGetLocalNewsSuccess(articleList) }, { getNewsFromApi() })

        mDisposable?.add(disposable)
    }

    /**
     * Dispose of disposables.
     */
    private fun dispose() {
        if (mDisposable?.isDisposed == false)
            mDisposable?.dispose()
    }

    /**
     * Subtract 5 minutes from current Date.
     *
     * @return Date.
     */
    private fun getDate() : Date {
        val calendar : Calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        calendar.add(Calendar.MINUTE, -1)

        return calendar.time
    }

    /**
     * Called when Articles are successfully returned from local database.
     */
    private fun onGetLocalNewsSuccess(articleList : List<Article>) {
        if (articleList.isNotEmpty()) {
            // Articles were found in local database. Show them in view.
            mView.showProgressLoader(false)
            mView.showArticles(articleList)
        } else
            // No articles in local database. Try to get them from api.
            getNewsFromApi()
    }

    /**
     * Try to get news from NewsApi.
     */
    private fun getNewsFromApi() {
        val disposable: Disposable = mModel.getRemoteArticles(NewsSourceNames.BBC.getString(), "top")
                .subscribe(
                        { articleList: List<Article> ->
                            mView.showProgressLoader(false)
                            mView.showArticles(articleList)
                        },
                        {
                            mView.showProgressLoader(false)
                            mView.showErrorMessage()
                        })

        mDisposable?.add(disposable)
    }

}
