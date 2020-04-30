package ir.alirezanazari.data.repository

import io.reactivex.Single
import ir.alirezanazari.data.net.NetworkDataManager
import ir.alirezanazari.domain.entities.MovieEntity
import ir.alirezanazari.domain.repository.MovieRepository


class MovieRepositoryImpl(
    private val net: NetworkDataManager
) : MovieRepository{

    override fun getMoviesList(page: Int): Single<List<MovieEntity>> {
        return net.getMoviesList(page)
    }

    override fun getMovieDetail(id: Long): Single<MovieEntity> {
        return net.getMovieDetail(id)
    }

}