package krasnikov.project.pmfightacademy.activity.activities.planned.ui

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import krasnikov.project.pmfightacademy.activity.activities.planned.data.PlannedActivitiesRepository
import krasnikov.project.pmfightacademy.activity.activities.planned.ui.mapper.PlannedActivityUIMapper
import krasnikov.project.pmfightacademy.activity.activities.planned.ui.model.PlannedActivityUIModel
import krasnikov.project.pmfightacademy.app.pagination.PaginationData
import krasnikov.project.pmfightacademy.app.pagination.PaginationState
import krasnikov.project.pmfightacademy.app.ui.base.BaseViewModel
import krasnikov.project.pmfightacademy.utils.Event
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class PlannedActivitiesViewModel @Inject constructor(
    private val plannedActivitiesRepository: PlannedActivitiesRepository,
    private val plannedActivityUIMapper: PlannedActivityUIMapper
) : BaseViewModel() {

    private val _plannedActivitiesContent =
        MutableStateFlow<PaginationData<PlannedActivityUIModel>>(
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
                    plannedActivityUIMapper.map(activities),
                    PaginationState.Complete
                )
            }
        }
    }

    fun loadPlannedActivities(forceRefresh: Boolean = false) {
        _plannedActivitiesContent.value =
            _plannedActivitiesContent.value.copy(currentState = PaginationState.Loading)
        viewModelScope.launch(exceptionHandler) {
            plannedActivitiesRepository.loadPlannedActivities(forceRefresh)
        }
    }

    fun openBooking() {
        viewModelScope.launch {
            eventChannel.send(
                Event.Navigation(PlannedActivitiesFragmentDirections.actionActivitiesToBooking())
            )
        }
    }

    override fun handleError(throwable: Throwable) {
        _plannedActivitiesContent.value =
            _plannedActivitiesContent.value.copy(currentState = PaginationState.Error(throwable as Exception))
    }
}
