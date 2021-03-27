package krasnikov.project.pmfightacademy.activity.booking.services.ui

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import krasnikov.project.pmfightacademy.activity.booking.services.data.ServiceRepository
import krasnikov.project.pmfightacademy.activity.booking.services.ui.mapper.ServiceUIMapper
import krasnikov.project.pmfightacademy.activity.booking.services.ui.model.ServiceUIModel
import krasnikov.project.pmfightacademy.app.domain.ResolveGeneralErrorUseCase
import krasnikov.project.pmfightacademy.app.pagination.PaginationData
import krasnikov.project.pmfightacademy.app.pagination.PaginationState
import krasnikov.project.pmfightacademy.app.ui.base.BaseViewModel
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ServicesViewModel @Inject constructor(
    private val serviceRepository: ServiceRepository,
    private val resolveGeneralErrorUseCase: ResolveGeneralErrorUseCase,
    private val serviceUIMapper: ServiceUIMapper
) : BaseViewModel() {

    private val _contentServices = MutableStateFlow<PaginationData<ServiceUIModel>>(
        PaginationData(
            emptyList(),
            PaginationState.Loading
        )
    )
    val contentServices = _contentServices.asStateFlow()

    init {
        viewModelScope.launch(exceptionHandler) {
            serviceRepository.flowData.collect {
                _contentServices.value =
                    PaginationData(serviceUIMapper.map(it), PaginationState.Complete)
            }
        }
        loadNextData()
    }

    override fun handleError(throwable: Throwable) {
        _contentServices.value =
            _contentServices.value.copy(
                currentState = PaginationState.Error(
                    resolveGeneralErrorUseCase.execute(
                        throwable
                    )
                )
            )
    }

    fun loadNextData() {
        _contentServices.value = _contentServices.value.copy(currentState = PaginationState.Loading)
        viewModelScope.launch(exceptionHandler) {
            serviceRepository.loadNextData()
        }
    }
}
