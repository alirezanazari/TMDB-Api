package ir.alirezanazari.domain.intractors.movie

import io.reactivex.Scheduler
import io.reactivex.Single
import ir.alirezanazari.domain.entities.MovieEntity
import ir.alirezanazari.domain.intractors.UseCase
import ir.alirezanazari.domain.repository.MovieRepository


class GetMoviesList(
    private val repository: MovieRepository ,
    io: Scheduler ,
    ui: Scheduler
): UseCase<List<MovieEntity> , Int>(io , ui) {

    override fun build(page: Int): Single<List<MovieEntity>> {
        return repository.getMoviesList(page)
    }
}