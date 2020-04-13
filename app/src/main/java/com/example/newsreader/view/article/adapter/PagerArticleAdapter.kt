package com.example.newsreader.view.article.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.newsreader.utils.models.Article

class PagerArticleAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    private var mDataset: List<Article> = ArrayList(0)

    /**
     * Provide a new Fragment associated with the specified position.
     *
     * @param position The position of the item within the adapter's data set.
     */
    override fun createFragment(position: Int): Fragment {
        return PagerArticleFragment.newInstance(mDataset[position])
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    override fun getItemCount(): Int = mDataset.size

    /**
     * Set this adapters dataset.
     *
     * @param articleList List to set as this adapter's dataset.
     */
    fun setDataset(articleList: List<Article>) {
        mDataset = articleList

        notifyDataSetChanged()
    }

}
