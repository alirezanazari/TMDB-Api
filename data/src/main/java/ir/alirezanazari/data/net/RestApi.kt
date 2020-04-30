package ir.alirezanazari.data.net

import io.reactivex.Single
import ir.alirezanazari.data.net.models.movie.MoviesListResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface RestApi {

    @GET("movie/upcoming")
    fun getMovies(
        @Query("page") page: Int
    ): Single<MoviesListResponse>
}