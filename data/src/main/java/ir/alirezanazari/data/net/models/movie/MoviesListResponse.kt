package ir.alirezanazari.data.net.models.movie


import com.google.gson.annotations.SerializedName

data class MoviesListResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Result>
)