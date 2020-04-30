package ir.alirezanazari.domain.repository

import io.reactivex.Single
import ir.alirezanazari.domain.entities.MovieEntity


interface MovieRepository {

    fun getMoviesList(page: Int) : Single<List<MovieEntity>>
    fun getMovieDetail(id: Long) : Single<MovieEntity>
}