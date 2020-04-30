package ir.alirezanazari.data.net

import io.reactivex.Single
import ir.alirezanazari.domain.entities.MovieEntity


interface NetworkDataManager {

    fun getMoviesList(page: Int) : Single<List<MovieEntity>>
    fun getMovieDetail(id: Long) : Single<MovieEntity>
}