package ir.alirezanazari.tmdbapi.ui.movieList

import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import ir.alirezanazari.domain.entities.MovieEntity
import ir.alirezanazari.domain.intractors.movie.GetMoviesList
import ir.alirezanazari.tmdbapi.R
import ir.alirezanazari.tmdbapi.internal.SingleLiveEvent
import ir.alirezanazari.tmdbapi.ui.BaseViewModel

class MoviesListViewModel(
    private val useCase: GetMoviesList
) : BaseViewModel(useCase) {

    var isLoading = false
    val responseListener = SingleLiveEvent<List<MovieEntity>>()

    fun getMovies(page: Int){
        setLoaderState(true)
        isLoading = true
        useCase.execute(object : DisposableSingleObserver<List<MovieEntity>>(){
            override fun onSuccess(resp: List<MovieEntity>) {
                isLoading = false
                responseListener.postValue(resp)
                setLoaderState(false)
                if (resp.isEmpty()) errorVisibilityListener.postValue(true)
            }

            override fun onError(e: Throwable) {
                isLoading = false
                errorListener.postValue(R.string.error_connection)
                if (page == 1) setLoaderState(false , isEffectRetry = true)
            }

        } , page)
    }
}
