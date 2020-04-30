package ir.alirezanazari.data.net.models.movie


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("poster_path")
    val posterPath: String?= null,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("id")
    val id: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("vote_count")
    val voteCount: Int,
    @SerializedName("vote_average")
    val voteAverage: Double
)