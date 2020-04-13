package com.example.newsreader.view.article.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.newsreader.R
import com.example.newsreader.databinding.PagerArticleItemBinding
import com.example.newsreader.utils.models.Article

class PagerArticleFragment() : Fragment() {

    companion object {
        const val KEY_ARTICLE: String = "KEY_ARTICLE"

        fun newInstance(article: Article): PagerArticleFragment {
            val fragment: PagerArticleFragment = PagerArticleFragment()

            val args: Bundle = Bundle()
            args.putParcelable(KEY_ARTICLE, article)

            fragment.arguments = args

            return fragment
        }
    }

    private var mBinding: PagerArticleItemBinding? = null

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate any views in the fragment,
     * @param container          Parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = PagerArticleItemBinding.inflate(inflater, container, false)

        return mBinding?.root
    }

    /**
     * Called immediately after #onCreateView().
     *
     * @param view               The View returned by #onCreateView().
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get the Article that was passed to this Fragment.
        val bundle: Bundle? = arguments

        val article: Article? = bundle?.getParcelable(KEY_ARTICLE)

        showArticleData(article)
    }

    override fun onDestroy() {
        super.onDestroy()

        mBinding = null
    }

    /**
     * Show Article data in View.
     *
     * @param article Article to show in Fragment's view.
     */
    private fun showArticleData(article: Article?) {
        // Set article title.
        mBinding?.textArticleTitle?.text = article?.title

        // Set article description.
        mBinding?.textArticleDescription?.text = article?.description

        // Set article image.
        mBinding?.let {
            Glide.with(it.root)
                    .load(article?.urlToImage)
                    .apply(RequestOptions()
                            .placeholder(R.drawable.ic_image_black_24dp)
                            .error(R.drawable.ic_broken_image_black_24dp))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(it.imageArticleImage)
        }
    }

}
