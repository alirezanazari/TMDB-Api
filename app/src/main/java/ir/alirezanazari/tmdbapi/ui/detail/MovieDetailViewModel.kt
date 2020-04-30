package ir.alirezanazari.tmdbapi.ui.detail

import io.reactivex.observers.DisposableSingleObserver
import ir.alirezanazari.domain.entities.MovieEntity
import ir.alirezanazari.domain.intractors.movie.GetMovieDetail
import ir.alirezanazari.tmdbapi.R
import ir.alirezanazari.tmdbapi.internal.Logger
import ir.alirezanazari.tmdbapi.internal.SingleLiveEvent
import ir.alirezanazari.tmdbapi.ui.BaseViewModel

class MovieDetailViewModel(
    private val useCase: GetMovieDetail
) : BaseViewModel(useCase) {

    val responseListener = SingleLiveEvent<MovieEntity>()

    fun getMovieById(id: Long) {
        setLoaderState(true)
        useCase.execute(object : DisposableSingleObserver<MovieEntity>() {
            override fun onSuccess(resp: MovieEntity) {
                setLoaderState(false)
                responseListener.postValue(resp)
            }

            override fun onError(e: Throwable) {
                setLoaderState(false, isEffectRetry = true)
                errorListener.postValue(R.string.error_connection)
                Logger.showLog(e.message)
            }

        }, id)
    }


}
