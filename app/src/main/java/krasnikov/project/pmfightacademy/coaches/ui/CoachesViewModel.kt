package krasnikov.project.pmfightacademy.coaches.ui

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import krasnikov.project.pmfightacademy.app.domain.ResolveGeneralErrorUseCase
import krasnikov.project.pmfightacademy.app.pagination.PaginationData
import krasnikov.project.pmfightacademy.app.pagination.PaginationState
import krasnikov.project.pmfightacademy.app.ui.base.BaseViewModel
import krasnikov.project.pmfightacademy.coaches.data.CoachRepository
import krasnikov.project.pmfightacademy.coaches.ui.mapper.CoachUIMapper
import krasnikov.project.pmfightacademy.coaches.ui.model.CoachUIModel
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CoachesViewModel @Inject constructor(
    private val coachRepository: CoachRepository,
    private val resolveGeneralErrorUseCase: ResolveGeneralErrorUseCase,
    private val coachUIMapper: CoachUIMapper
) : BaseViewModel() {

    private val _contentCoaches = MutableStateFlow<PaginationData<CoachUIModel>>(
        PaginationData(
            emptyList(),
            PaginationState.Loading
        )
    )
    val contentCoaches = _contentCoaches.asStateFlow()

    init {
        viewModelScope.launch(exceptionHandler) {
            coachRepository.flowData.collect {
                _contentCoaches.value =
                    PaginationData(coachUIMapper.map(it), PaginationState.Complete)
            }
        }
    }

    override fun handleError(throwable: Throwable, coroutineName: CoroutineName?) {
        _contentCoaches.value =
            _contentCoaches.value.copy(
                currentState = PaginationState.Error(
                    resolveGeneralErrorUseCase.execute(throwable)
                )
            )
    }

    fun loadCoaches(forceRefresh: Boolean = false) {
        _contentCoaches.value = _contentCoaches.value.copy(currentState = PaginationState.Loading)
        viewModelScope.launch(exceptionHandler) {
            coachRepository.loadNextData(forceRefresh)
        }
    }
}
