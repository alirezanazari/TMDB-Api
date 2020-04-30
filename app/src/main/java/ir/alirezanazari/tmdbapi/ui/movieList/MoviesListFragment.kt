package ir.alirezanazari.tmdbapi.ui.movieList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.alirezanazari.domain.entities.MovieEntity
import ir.alirezanazari.tmdbapi.R
import ir.alirezanazari.tmdbapi.internal.Logger
import ir.alirezanazari.tmdbapi.internal.Navigator
import ir.alirezanazari.tmdbapi.ui.BaseFragment
import kotlinx.android.synthetic.main.movies_list_fragment.*
import org.koin.android.ext.android.inject

class MoviesListFragment : BaseFragment() {

    private val viewModel: MoviesListViewModel by inject()
    private val mAdapter: MovieListAdapter by inject()
    private var mCurrentPage = 1
    private var isEndOfList = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movies_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupMovieList()
    }

    private fun setupMovieList() {
        val lManager = LinearLayoutManager(rvMovies.context)
        rvMovies.apply {
            layoutManager = lManager
            adapter = mAdapter
        }

        mAdapter.onClick = { id ->
            id?.let {
                //open detail fragment
                Navigator.openMovieDetail(it , activity?.supportFragmentManager)
            }
        }

        var visibleItemCount: Int
        var totalItemCount: Int
        var pastItemCount: Int

        rvMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    visibleItemCount = lManager.childCount
                    totalItemCount = lManager.itemCount
                    pastItemCount = lManager.findFirstVisibleItemPosition()

                    if (!viewModel.isLoading && !isEndOfList) {
                        if ((visibleItemCount + pastItemCount) >= totalItemCount) {
                            viewModel.getMovies(page = mCurrentPage)
                        }
                    }
                }
            }
        })

        viewModel.getMovies(page = mCurrentPage)
    }

    private fun setupListeners() {

        btnRetry.setOnClickListener {
            mCurrentPage = 1
            viewModel.getMovies(mCurrentPage)
        }

        viewModel.responseListener.observe(viewLifecycleOwner, Observer {
            handleMoviesResponse(it)
        })

        viewModel.errorListener.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (mCurrentPage == 1) tvError.setText(it)
                Logger.showToast(activity, it)
            }
        })

        viewModel.loaderVisibilityListener.observe(viewLifecycleOwner, Observer {
            it?.let { state ->
                pbLoader.visibility = if (state) View.VISIBLE else View.GONE
            }
        })

        viewModel.errorVisibilityListener.observe(viewLifecycleOwner, Observer {
            it?.let { state ->
                if (mCurrentPage == 11) {
                    tvError.visibility = if (state) View.VISIBLE else View.GONE
                }
            }
        })

        viewModel.retryVisibilityListener.observe(viewLifecycleOwner, Observer {
            it?.let { state ->
                btnRetry.visibility = if (state) View.VISIBLE else View.GONE
            }
        })
    }

    private fun handleMoviesResponse(items: List<MovieEntity>) {
        if (mCurrentPage != 1) {
            mAdapter.removeLoader()
        }

        mAdapter.setItems(items)
        mCurrentPage++

        if (items.isNotEmpty()) {
            mAdapter.addLoader()
        } else {
            isEndOfList = true
        }

    }

    override fun onBackPressed(): Boolean {
        return true
    }

}
