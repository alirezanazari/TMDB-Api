package ir.alirezanazari.data.net

import io.reactivex.Single
import ir.alirezanazari.domain.entities.MovieEntity

class NetworkDataManagerImpl(
    private val api: RestApi
) : NetworkDataManager {

    override fun getMoviesList(page: Int): Single<List<MovieEntity>> {
        return api.getMovies(page).map {response->
            response.results.map {
                MovieEntity(
                    it.id ,
                    it.voteCount ,
                    it.posterPath,
                    it.title ,
                    it.voteAverage,
                    it.overview,
                    it.releaseDate
                )
            }
        }
    }

    override fun getMovieDetail(id: Long): Single<MovieEntity> {
        return api.getMovieDetail(id).map {
            MovieEntity(
                it.id ,
                it.voteCount ,
                it.posterPath,
                it.title ,
                it.voteAverage,
                it.overview,
                it.releaseDate
            )
        }
    }

}