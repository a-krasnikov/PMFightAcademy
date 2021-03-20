package krasnikov.project.pmfightacademy.app.ui.base

import androidx.lifecycle.ViewModel
import androidx.navigation.NavAction
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import krasnikov.project.pmfightacademy.utils.NavigationEvent

abstract class BaseViewModel : ViewModel() {
    protected abstract val exceptionHandler: CoroutineExceptionHandler

    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

}