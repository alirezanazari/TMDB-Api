package ir.alirezanazari.domain.intractors.movie

import io.reactivex.Scheduler
import io.reactivex.Single
import ir.alirezanazari.domain.entities.MovieEntity
import ir.alirezanazari.domain.intractors.UseCaseSingle
import ir.alirezanazari.domain.repository.MovieRepository


class GetMovieDetail(
    private val repository: MovieRepository,
    io: Scheduler,
    ui: Scheduler
): UseCaseSingle<MovieEntity , Long>(io , ui) {

    override fun build(param: Long): Single<MovieEntity> {
        return repository.getMovieDetail(param)
    }
}