package krasnikov.project.pmfightacademy.activities.planned.ui

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import krasnikov.project.pmfightacademy.activities.planned.data.PlannedActivitiesRepository
import krasnikov.project.pmfightacademy.app.pagination.PaginationData
import krasnikov.project.pmfightacademy.app.pagination.PaginationState
import krasnikov.project.pmfightacademy.app.ui.base.BaseViewModel
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class PlannedActivitiesViewModel
@Inject constructor(private val plannedActivitiesRepository: PlannedActivitiesRepository) :
    BaseViewModel() {

    private val _plannedActivitiesContent =
        MutableStateFlow<PaginationData<krasnikov.project.pmfightacademy.activities.data.Activity>>(
            PaginationData(
                emptyList(),
                PaginationState.Loading
            )
        )

    val plannedActivitiesContent = _plannedActivitiesContent.asStateFlow()

    init {
        viewModelScope.launch(exceptionHandler) {
            plannedActivitiesRepository.plannedActivitiesFlow.collect { activities ->
                _plannedActivitiesContent.value = PaginationData(
                    activities,
                    PaginationState.Complete
                )
            }
        }
        getPlannedActivities()
    }

    fun getPlannedActivities() {
        _plannedActivitiesContent.value = _plannedActivitiesContent.value.stateToLoading()
        viewModelScope.launch(exceptionHandler) {
            plannedActivitiesRepository.getPlannedActivities()
        }
    }


    override fun handleError(throwable: Throwable) {
        _plannedActivitiesContent.value = _plannedActivitiesContent.value.stateToError(throwable as Exception)
    }
}
