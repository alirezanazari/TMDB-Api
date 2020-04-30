package ir.alirezanazari.tmdbapi.internal

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ir.alirezanazari.data.net.ApiConfig
import ir.alirezanazari.data.net.NetworkDataManager
import ir.alirezanazari.data.net.NetworkDataManagerImpl
import ir.alirezanazari.data.net.RequestInterceptor
import ir.alirezanazari.data.repository.MovieRepositoryImpl
import ir.alirezanazari.domain.intractors.movie.GetMovieDetail
import ir.alirezanazari.domain.intractors.movie.GetMoviesList
import ir.alirezanazari.domain.repository.MovieRepository
import ir.alirezanazari.tmdbapi.ui.detail.MovieDetailViewModel
import ir.alirezanazari.tmdbapi.ui.movieList.MovieListAdapter
import ir.alirezanazari.tmdbapi.ui.movieList.MoviesListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {

    single { ImageLoader() }
    single { RequestInterceptor() }
    single { ApiConfig(get()) }
    single<NetworkDataManager> { NetworkDataManagerImpl(get()) }

    factory<MovieRepository> { MovieRepositoryImpl(get()) }
    factory { GetMoviesList(get() , Schedulers.io() , AndroidSchedulers.mainThread()) }
    factory { GetMovieDetail(get() , Schedulers.io() , AndroidSchedulers.mainThread()) }
    factory { MovieListAdapter(get()) }

    viewModel { MoviesListViewModel(get()) }
    viewModel { MovieDetailViewModel(get()) }
}