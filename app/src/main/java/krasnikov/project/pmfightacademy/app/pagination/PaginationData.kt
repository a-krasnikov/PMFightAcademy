package krasnikov.project.pmfightacademy.app.pagination

import java.lang.Exception

data class PaginationData<T>(
    val availableData: List<T> = emptyList(),
    val currentState: PaginationState
)

sealed class PaginationState {
    object Complete : PaginationState()

    object Loading : PaginationState()

    data class Error(val error: Exception) : PaginationState()
}
