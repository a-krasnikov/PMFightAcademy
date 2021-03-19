package krasnikov.project.pmfightacademy.app.pagination

import java.lang.Exception

class PaginationData<T>(
    val availableData: List<T> = emptyList(),
    val currentState: PaginationState
) {

    fun stateToLoading(): PaginationData<T> =
        PaginationData(availableData, PaginationState.Loading)

    fun stateToError(exception: Exception): PaginationData<T> =
        PaginationData(availableData, PaginationState.Error(exception))
}

sealed class PaginationState {
    object Complete : PaginationState()

    object Loading : PaginationState()

    data class Error(val error: Exception) : PaginationState()
}
