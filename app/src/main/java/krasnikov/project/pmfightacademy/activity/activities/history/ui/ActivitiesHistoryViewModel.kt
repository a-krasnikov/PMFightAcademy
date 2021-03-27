package krasnikov.project.pmfightacademy.activity.activities.history.ui

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import krasnikov.project.pmfightacademy.activity.activities.history.data.ActivitiesHistoryRepository
import krasnikov.project.pmfightacademy.activity.activities.history.ui.mapper.CompletedActivityUIMapper
import krasnikov.project.pmfightacademy.activity.activities.history.ui.model.CompletedActivityUIModel
import krasnikov.project.pmfightacademy.app.domain.ResolveGeneralErrorUseCase
import krasnikov.project.pmfightacademy.app.pagination.PaginationData
import krasnikov.project.pmfightacademy.app.pagination.PaginationState
import krasnikov.project.pmfightacademy.app.ui.base.BaseViewModel
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ActivitiesHistoryViewModel @Inject constructor(
    private val activitiesHistoryRepository: ActivitiesHistoryRepository,
    private val resolveGeneralErrorUseCase: ResolveGeneralErrorUseCase,
    private val completedActivityUIMapper: CompletedActivityUIMapper
) : BaseViewModel() {

    private val _activitiesHistoryContent =
        MutableStateFlow<PaginationData<CompletedActivityUIModel>>(
            PaginationData(
                emptyList(),
                PaginationState.Loading
            )
        )

    val activitiesHistoryContent = _activitiesHistoryContent.asStateFlow()

    init {
        viewModelScope.launch(exceptionHandler) {
            activitiesHistoryRepository.activitiesHistoryFlow.collect { activities ->
                _activitiesHistoryContent.value = PaginationData(
                    completedActivityUIMapper.map(activities),
                    PaginationState.Complete
                )
            }
        }
    }

    fun loadActivitiesHistory(forceRefresh: Boolean = false) {
        _activitiesHistoryContent.value =
            _activitiesHistoryContent.value.copy(currentState = PaginationState.Loading)
        viewModelScope.launch(exceptionHandler) {
            activitiesHistoryRepository.loadActivitiesHistory(forceRefresh)
        }
    }

    override fun handleError(throwable: Throwable) {
        _activitiesHistoryContent.value =
            _activitiesHistoryContent.value.copy(
                currentState = PaginationState.Error(
                    resolveGeneralErrorUseCase.execute(throwable)
                )
            )
    }
}
