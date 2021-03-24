package krasnikov.project.pmfightacademy.activities.history.ui

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import krasnikov.project.pmfightacademy.activities.data.Activity
import krasnikov.project.pmfightacademy.activities.history.data.ActivitiesHistoryRepository
import krasnikov.project.pmfightacademy.activities.planned.data.PlannedActivitiesRepository
import krasnikov.project.pmfightacademy.app.pagination.PaginationData
import krasnikov.project.pmfightacademy.app.pagination.PaginationState
import krasnikov.project.pmfightacademy.app.ui.base.BaseViewModel
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ActivitiesHistoryViewModel
@Inject constructor(private val activitiesHistoryRepository: ActivitiesHistoryRepository) :
    BaseViewModel() {

    private val _activitiesHistoryContent =
        MutableStateFlow<PaginationData<Activity>>(
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
                    activities,
                    PaginationState.Complete
                )
            }
        }
        getActivitiesHistory()
    }

    fun getActivitiesHistory() {
        _activitiesHistoryContent.value = _activitiesHistoryContent.value.stateToLoading()
        viewModelScope.launch(exceptionHandler) {
            activitiesHistoryRepository.getActivitiesHistory()
        }
    }


    override fun handleError(throwable: Throwable) {
        _activitiesHistoryContent.value =
            _activitiesHistoryContent.value.stateToError(throwable as Exception)
    }
}
