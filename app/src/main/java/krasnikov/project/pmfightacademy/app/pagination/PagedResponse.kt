package krasnikov.project.pmfightacademy.app.pagination

import kotlinx.serialization.SerialName

data class PagedResponse<T>(
    @SerialName("data")
    val data: List<T>,
    @SerialName("paggination")
    val pagination: PagingParams
)

data class PagingParams(
    @SerialName("page")
    val page: Int,
    @SerialName("totalPages")
    val totalPages: Int,
    @SerialName("hasPreviousPage")
    val hasPreviousPage: Boolean,
    @SerialName("hasNextPage")
    val hasNextPage: Boolean
)