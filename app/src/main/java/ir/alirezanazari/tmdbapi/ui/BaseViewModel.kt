package ir.alirezanazari.tmdbapi.ui

import androidx.lifecycle.ViewModel
import ir.alirezanazari.domain.intractors.UseCase
import ir.alirezanazari.tmdbapi.internal.SingleLiveEvent


open class BaseViewModel(
    private val useCase: UseCase? = null
) : ViewModel() {

    val loaderVisibilityListener = SingleLiveEvent<Boolean>()
    val retryVisibilityListener = SingleLiveEvent<Boolean>()
    val errorVisibilityListener = SingleLiveEvent<Boolean>()
    val errorListener = SingleLiveEvent<Int>()

    override fun onCleared() {
        useCase?.clearDisposable()
        super.onCleared()
    }

    fun setLoaderState(state: Boolean , isEffectRetry: Boolean = false){
        if (state){
            loaderVisibilityListener.postValue(true)
            retryVisibilityListener.postValue(false)
            errorVisibilityListener.postValue(false)
        }else{
            loaderVisibilityListener.postValue(false)
            errorVisibilityListener.postValue(isEffectRetry)
            retryVisibilityListener.postValue(isEffectRetry)
        }
    }

}