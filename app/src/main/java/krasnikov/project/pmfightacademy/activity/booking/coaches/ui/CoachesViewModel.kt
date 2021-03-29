package krasnikov.project.pmfightacademy.activity.booking.coaches.ui

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import krasnikov.project.pmfightacademy.activity.booking.coaches.data.CoachRepository
import krasnikov.project.pmfightacademy.activity.booking.coaches.ui.mapper.CoachUIMapper
import krasnikov.project.pmfightacademy.activity.booking.coaches.ui.model.CoachUIModel
import krasnikov.project.pmfightacademy.app.pagination.PaginationData
import krasnikov.project.pmfightacademy.app.pagination.PaginationState
import krasnikov.project.pmfightacademy.app.ui.base.BaseViewModel
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CoachesViewModel @Inject constructor(
    private val coachRepository: CoachRepository,
    private val coachUIMapper: CoachUIMapper
) : BaseViewModel() {

    private val _contentCoaches = MutableStateFlow<PaginationData<CoachUIModel>>(
        PaginationData(
            emptyList(),
            PaginationState.Loading
        )
    )
    val contentCoaches = _contentCoaches.asStateFlow()

    override fun handleError(throwable: Throwable) {
        _contentCoaches.value =
            _contentCoaches.value.copy(currentState = PaginationState.Error(throwable as Exception))
    }

    fun loadData(serviceId: Int) {
        viewModelScope.launch(exceptionHandler) {
            coachRepository.flowData(serviceId).collect {
                _contentCoaches.value =
                    PaginationData(coachUIMapper.map(it), PaginationState.Complete)
            }
        }
        loadNextData()
    }

    fun loadNextData() {
        _contentCoaches.value = _contentCoaches.value.copy(currentState = PaginationState.Loading)
        viewModelScope.launch(exceptionHandler) {
            coachRepository.loadNextData()
        }
    }
}
