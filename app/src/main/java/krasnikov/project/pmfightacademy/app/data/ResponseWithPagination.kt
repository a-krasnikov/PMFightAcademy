package krasnikov.project.pmfightacademy.app.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseWithPaginationModel<T>(
    @SerialName("data")
    val data: List<T>,
    @SerialName("pagination")
    val pagination: PaginationModel
)

@Serializable
data class PaginationModel(
    @SerialName("page")
    val page: Int,
    @SerialName("totalPages")
    val totalPages: Int,
    @SerialName("hasPreviousPage")
    val hasPreviousPage: Boolean,
    @SerialName("hasNextPage")
    val hasNextPage: Boolean,
)
