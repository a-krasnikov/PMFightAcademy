package krasnikov.project.pmfightacademy.coaches.ui

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import krasnikov.project.pmfightacademy.app.base.BaseViewModel
import krasnikov.project.pmfightacademy.app.pagination.PaginationData
import krasnikov.project.pmfightacademy.app.pagination.PaginationState
import krasnikov.project.pmfightacademy.app.ui.base.BaseViewModel
import krasnikov.project.pmfightacademy.coaches.data.Coach
import krasnikov.project.pmfightacademy.coaches.data.CoachRepository
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CoachesViewModel @Inject constructor(private val coachRepository: CoachRepository) :
    BaseViewModel() {

    private val _contentCoaches = MutableStateFlow<PaginationData<Coach>>(
        PaginationData(
            emptyList(),
            PaginationState.Loading
        )
    )
    val contentCoaches = _contentCoaches.asStateFlow()

    init {
        viewModelScope.launch(exceptionHandler) {
            coachRepository.flowData.collect {
                _contentCoaches.value = PaginationData(it, PaginationState.Complete)
            }
        }
        loadNextData()
    }

    override fun handleError(throwable: Throwable) {
        _contentCoaches.value.stateToError(throwable as Exception)
    }

    fun loadNextData() {
        _contentCoaches.value = _contentCoaches.value.stateToLoading()
        viewModelScope.launch(exceptionHandler) {
            coachRepository.loadNextData()
        }
    }
}
