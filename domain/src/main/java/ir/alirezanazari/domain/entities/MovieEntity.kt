package ir.alirezanazari.domain.entities

data class MovieEntity(
    val id: Long,
    val voteCount: Int,
    val posterPath: String?,
    val title: String,
    val voteAverage: Double,
    val overview: String?,
    val releaseDate: String
)