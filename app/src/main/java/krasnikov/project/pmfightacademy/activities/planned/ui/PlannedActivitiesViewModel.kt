package krasnikov.project.pmfightacademy.activities.planned.ui

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import krasnikov.project.pmfightacademy.activities.data.ActivitiesRepository
import krasnikov.project.pmfightacademy.app.pagination.PaginationData
import krasnikov.project.pmfightacademy.app.pagination.PaginationState
import krasnikov.project.pmfightacademy.app.ui.base.BaseViewModel
import krasnikov.project.pmfightacademy.utils.State
import javax.inject.Inject

@HiltViewModel
class PlannedActivitiesViewModel @Inject constructor(private val activitiesRepository: ActivitiesRepository) :
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
            activitiesRepository.plannedActivitiesFlow.collect { activities ->
                _plannedActivitiesContent.value = PaginationData(
                    activities,
                    PaginationState.Complete
                )
            }
        }
    }

    fun getPlannedActivities() {
        _plannedActivitiesContent.value = _plannedActivitiesContent.value.stateToLoading()
        viewModelScope.launch(exceptionHandler) {
            activitiesRepository.getPlannedActivities()
        }
    }


    override fun handleError(throwable: Throwable) {
        TODO("Not yet implemented")
    }
}
