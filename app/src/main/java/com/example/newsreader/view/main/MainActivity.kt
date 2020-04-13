package com.example.newsreader.view.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsreader.databinding.ActivityMainBinding
import com.example.newsreader.utils.dialogs.ErrorDialog
import com.example.newsreader.utils.models.Article
import com.example.newsreader.view.article.ArticleActivity
import com.example.newsreader.view.main.adapter.RecyclerArticleAdapter
import org.koin.android.scope.currentScope
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity(), MainMvpView, RecyclerArticleAdapter.OnArticleClickListener {

    companion object {
        const val EXTRA_ARTICLE_INDEX: String = "EXTRA_ARTICLE_INDEX"
    }

    private val mPresenter: MainMvpPresenter by currentScope.inject { parametersOf(this) }

    private val mRecyclerArticleAdapter: RecyclerArticleAdapter by currentScope.inject()

    private var mBinding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)

        // Set view binding.
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding?.root)

        // Make view's content to appear behind the navigation bar.
        mBinding?.root?.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

        initRecyclerArticle()
    }

    override fun onResume() {
        super.onResume()

        mPresenter.ready()
    }

    override fun onPause() {
        super.onPause()

        mPresenter.stop()
    }

    override fun onDestroy() {
        super.onDestroy()

        mBinding = null
    }

    /**
     * Callback that will be called.
     *
     * @param articlePosition Position of the item in the adapter's data set.
     */
    override fun onArticleClick(articlePosition: Int) {
        mPresenter.articleSelected(articlePosition)
    }

    /**
     * Starts ArticleActivity.
     *
     * @param articleIndex Position index of selected article item.
     */
    override fun startArticleActivity(articleIndex : Int) {
        val intent: Intent = Intent(this, ArticleActivity::class.java)
        intent.putExtra(EXTRA_ARTICLE_INDEX, articleIndex)

        startActivity(intent)
    }

    /**
     * Display Articles list in view.
     *
     * @param articleList List of Articles to show.
     */
    override fun showArticles(articleList : List<Article>) = mRecyclerArticleAdapter.setDataset(articleList)

    /**
     * Shows ProgressBar in view.
     *
     * @param show True to show ProgressBar, false to hide it.
     */
    override fun showProgressLoader(show : Boolean) {
        if (show)
            mBinding?.progressLoader?.visibility = View.VISIBLE
        else
            mBinding?.progressLoader?.visibility = View.GONE
    }

    /**
     * Show error message to user.
     */
    override fun showErrorMessage() {
        val errorDialog: DialogFragment = ErrorDialog.newInstance()
        errorDialog.show(supportFragmentManager, "ErrorDialog")
    }

    /**
     * Initialize RecyclerView to show Articles list.
     */
    private fun initRecyclerArticle() {
        mBinding?.recyclerArticle?.layoutManager = LinearLayoutManager(this)
        mBinding?.recyclerArticle?.adapter = mRecyclerArticleAdapter

        mRecyclerArticleAdapter.setOnArticleClickListener(this)
    }

}
