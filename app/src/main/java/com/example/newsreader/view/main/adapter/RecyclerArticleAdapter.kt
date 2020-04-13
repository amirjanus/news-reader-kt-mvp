package com.example.newsreader.view.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.newsreader.R
import com.example.newsreader.databinding.RecyclerArticleItemBinding
import com.example.newsreader.utils.models.Article

class RecyclerArticleAdapter : RecyclerView.Adapter<RecyclerArticleAdapter.ArticleViewHolder>() {

    /**
     * Implement this interface to get notified when user clicks on a item.
     */
    interface OnArticleClickListener {
        /**
         * Callback that will be called.
         *
         * @param articlePosition Position of the item in the adapter's data set.
         */
        fun onArticleClick(articlePosition : Int)
    }

    private var mListener : OnArticleClickListener? = null

    private var mDataset : List<Article> = ArrayList(0)

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     *
     * @param parent   The ViewGroup into which the new View will be added.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : ArticleViewHolder {
        val binding : RecyclerArticleItemBinding = RecyclerArticleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ArticleViewHolder(binding, mListener)
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder   The ViewHolder which should be updated.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder : ArticleViewHolder, position : Int) {
        val current: Article = mDataset[position]

        // Set article text.
        holder.mBinding.textArticleTitle.text = current.title

        // Set article image.
        Glide.with(holder.mBinding.root)
                .load(current.urlToImage)
                .apply(RequestOptions()
                        .placeholder(R.drawable.ic_image_black_24dp)
                        .error(R.drawable.ic_broken_image_black_24dp))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.mBinding.imageArticleImage)
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    override fun getItemCount() : Int = mDataset.size

    /**
     * Register a callback to be invoked when Recycler item is clicked.
     *
     * @param listener Callback to be invoked.
     */
    fun setOnArticleClickListener(listener: OnArticleClickListener) {
        mListener = listener
    }

    /**
     * Set this adapters dataset.
     *
     * @param articleList List to set as this adapter's dataset.
     */
    fun setDataset(articleList: List<Article>) {
        mDataset = articleList

        notifyDataSetChanged()
    }

    class ArticleViewHolder(binding : RecyclerArticleItemBinding, listener : OnArticleClickListener?) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        private val mListener: OnArticleClickListener? = listener
        val mBinding: RecyclerArticleItemBinding = binding

        init {
            mBinding.root.setOnClickListener { v: View? -> onClick(v) }
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        override fun onClick(v : View?) {
            mListener?.onArticleClick(adapterPosition)
        }

    }

}
