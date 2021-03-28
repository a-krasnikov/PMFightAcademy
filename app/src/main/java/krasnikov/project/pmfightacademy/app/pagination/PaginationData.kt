package krasnikov.project.pmfightacademy.app.pagination

import krasnikov.project.pmfightacademy.utils.ErrorWrapper

data class PaginationData<T>(
    val availableData: List<T> = emptyList(),
    val currentState: PaginationState
)

sealed class PaginationState {
    object Empty : PaginationState()
    object Complete : PaginationState()
    object Loading : PaginationState()
    data class Error(val error: ErrorWrapper.General) : PaginationState()
}
