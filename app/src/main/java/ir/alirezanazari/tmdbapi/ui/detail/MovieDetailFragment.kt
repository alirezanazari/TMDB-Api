package ir.alirezanazari.tmdbapi.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import ir.alirezanazari.data.utils.Consts.IMAGE_BASE_URL
import ir.alirezanazari.tmdbapi.R
import ir.alirezanazari.tmdbapi.internal.ImageLoader
import ir.alirezanazari.tmdbapi.ui.BaseFragment
import kotlinx.android.synthetic.main.movie_detail_fragment.*
import org.koin.android.ext.android.inject

class MovieDetailFragment : BaseFragment() {

    companion object {
        const val ID_KEY = "id"
        fun newInstance(id: Long): MovieDetailFragment {
            return MovieDetailFragment().apply {
                arguments = bundleOf(ID_KEY to id)
            }
        }
    }

    private val imageLoader: ImageLoader by inject()
    private val viewModel: MovieDetailViewModel by inject()
    private var movieId: Long? = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movie_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieId = arguments?.getLong(ID_KEY) ?: 0
        setupListeners()
        viewModel.getMovieById(movieId!!)
    }

    private fun setupListeners() {

        btnRetry.setOnClickListener {
            viewModel.getMovieById(movieId!!)
        }

        viewModel.responseListener.observe(viewLifecycleOwner, Observer {
            tvName.text = it.title
            tvDescription.text = it.overview ?: "No Description"
            imageLoader.load(ivPicture, "$IMAGE_BASE_URL${it.posterPath}", R.drawable.place_holder)

        })

        viewModel.errorListener.observe(viewLifecycleOwner, Observer {
            it?.let {
                tvNoData.setText(it)
            }
        })

        viewModel.loaderVisibilityListener.observe(viewLifecycleOwner, Observer {
            it?.let { state ->
                pbLoading.visibility = if (state) View.VISIBLE else View.GONE
            }
        })

        viewModel.errorVisibilityListener.observe(viewLifecycleOwner, Observer {
            it?.let { state ->
                tvNoData.visibility = if (state) View.VISIBLE else View.GONE
            }
        })

        viewModel.retryVisibilityListener.observe(viewLifecycleOwner, Observer {
            it?.let { state ->
                btnRetry.visibility = if (state) View.VISIBLE else View.GONE
            }
        })
    }

    override fun onBackPressed(): Boolean {
        return true
    }
}
