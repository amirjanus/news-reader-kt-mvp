package com.example.newsreader.view.article

import com.example.newsreader.data.MainMvpModel
import com.example.newsreader.data.constants.NewsSourceNames
import com.example.newsreader.utils.models.Article
import io.reactivex.disposables.Disposable

class ArticlePresenter(articleMvpView: ArticleMvpView, mainMvpModel: MainMvpModel) : ArticleMvpPresenter {

    private val mView: ArticleMvpView = articleMvpView
    private val mModel: MainMvpModel = mainMvpModel

    private var mDisposable: Disposable? = null

    private var mArticleList: List<Article> = ArrayList(0)

    private var mIsGetArticlesDone: Boolean = false

    /**
     * Called when the view is ready to be used.
     */
    override fun ready() {}

    /**
     * Called when the view should not be used anymore.
     */
    override fun stop() {
        dispose()
    }

    /**
     * Called when new page is set in ViewPager.
     *
     * @param index Position index of the new ViewPager page.
     */
    override fun articlePageChanged(index : Int) {
        mView.showToolbarTitle(mArticleList[index].title)
    }

    /**
     * Get articles from database.
     *
     * @param articleIndex Position index of current ViewPager page.
     */
    override fun fetchArticles(articleIndex: Int) {
        // Don't request articles from local database if they were already fetched.
        if (mIsGetArticlesDone)
            return

        mDisposable = mModel.getLocalArticles(NewsSourceNames.BBC.getString())
                .subscribe(
                        { articleList: List<Article> ->
                            mArticleList = articleList

                            mView.showArticles(articleList, articleIndex)

                            mIsGetArticlesDone = true
                        },
                        {
                            mView.showErrorMessage()

                            mIsGetArticlesDone = true
                        })
    }

    /**
     * Dispose of disposables.
     */
    private fun dispose() {
        if (mDisposable?.isDisposed == false)
            mDisposable?.dispose()
    }

}