package com.example.newsreader.view.article

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.viewpager2.widget.ViewPager2
import com.example.newsreader.databinding.ActivityArticleBinding
import com.example.newsreader.utils.dialogs.ErrorDialog
import com.example.newsreader.utils.models.Article
import com.example.newsreader.view.article.adapter.PagerArticleAdapter
import com.example.newsreader.view.main.MainActivity
import org.koin.android.scope.currentScope
import org.koin.core.parameter.parametersOf

class ArticleActivity : AppCompatActivity(), ArticleMvpView {

    private val KEY_CURRENT_PAGE = "KEY_CURRENT_PAGE"

    private val mPresenter: ArticleMvpPresenter by currentScope.inject { parametersOf(this) }
    private val mPagerArticleAdapter: PagerArticleAdapter by currentScope.inject { parametersOf(this) }

    private var mBinding: ActivityArticleBinding? = null

    private lateinit var mOnPageChangeCallback: ViewPager2.OnPageChangeCallback

    private var mCurrentPage: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set view binding.
        mBinding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(mBinding?.root)

        // Make view's content to appear behind the navigation bar.
        mBinding?.root?.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

        // Get Article index from Intent that started this Activity.
        mCurrentPage = intent.getIntExtra(MainActivity.EXTRA_ARTICLE_INDEX, 0)

        initPagerArticles()
    }

    override fun onResume() {
        super.onResume()

        mPresenter.ready()

        mPresenter.fetchArticles(mCurrentPage)
    }

    override fun onPause() {
        super.onPause()

        mPresenter.stop()
    }

    override fun onDestroy() {
        super.onDestroy()

        mBinding?.pagerArticles?.unregisterOnPageChangeCallback(mOnPageChangeCallback)
        mBinding = null
    }

    override fun onSaveInstanceState(outState : Bundle) {
        mCurrentPage = mBinding?.pagerArticles?.currentItem ?: 0

        outState.putInt(KEY_CURRENT_PAGE, mCurrentPage)

        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState : Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        mCurrentPage = savedInstanceState?.getInt(KEY_CURRENT_PAGE) ?: 0
    }

    /**
     * Display Articles list in view.
     *
     * @param articleList List of Articles to show.
     * @param index       Position index of Article to show as current item.
     */
    override fun showArticles(articleList : List<Article>, index : Int) {
        mPagerArticleAdapter.setDataset(articleList)

        mBinding?.pagerArticles?.setCurrentItem(index, false)
    }

    /**
     * Show error message to user.
     */
    override fun showErrorMessage() {
        val errorDialog: DialogFragment = ErrorDialog.newInstance()
        errorDialog.show(supportFragmentManager, "ErrorDialog")
    }

    /**
     * Display title in app's toolbar.
     *
     * @param title String to show in toolbar.
     */
    override fun showToolbarTitle(title : String) {
        supportActionBar?.title = title
    }

    /**
     * Initialize articles ViewPager.
     */
    private fun initPagerArticles() {
        mOnPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            /**
             * This method will be invoked when a new page becomes selected.
             *
             * @param position Position index of the new selected page.
             */
            override fun onPageSelected(position: Int) {
                mPresenter.articlePageChanged(position)
            }
        }

        mBinding?.pagerArticles?.registerOnPageChangeCallback(mOnPageChangeCallback)

        mBinding?.pagerArticles?.adapter = mPagerArticleAdapter
    }

}
