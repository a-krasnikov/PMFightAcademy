package krasnikov.project.pmfightacademy.app

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import krasnikov.project.pmfightacademy.app.data.exception.RequestNotAuthorizedException

abstract class BaseViewModel : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { context, throwable ->
        handleError(throwable, context[CoroutineName])
    }
    protected val baseViewModelScope = CoroutineScope(SupervisorJob() + exceptionHandler)

    open fun handleError(throwable: Throwable, coroutineName: CoroutineName?) {
        if(throwable is RequestNotAuthorizedException) {
            _navigationEvent.postValue( NavigationEvent { Navigator.navigateToLogin(it) })
        }
    }

    protected val _navigationEvent = SingleLiveEvent<NavigationEvent>()
    val navigationEvent
        get() = _navigationEvent as LiveData<NavigationEvent>

    override fun onCleared() {
        baseViewModelScope.cancel()
        super.onCleared()
    }
}