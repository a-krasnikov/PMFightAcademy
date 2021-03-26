package krasnikov.project.pmfightacademy.app.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import krasnikov.project.pmfightacademy.MainContentDirections
import krasnikov.project.pmfightacademy.utils.Event

@Suppress("UnusedPrivateMember")
abstract class BaseViewModel : ViewModel() {

    protected val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleNotAuthorizedException(throwable)
        handleError(throwable)
    }

    protected val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventFlow = eventChannel.receiveAsFlow()

    private fun handleNotAuthorizedException(throwable: Throwable) {
        //TODO handleNotAuthorizedException --> navigateToLogin()
    }

    private fun navigateToLogin() {
        viewModelScope.launch {
            eventChannel.send(Event.Navigation(MainContentDirections.actionGlobalToLogin()))
        }
    }

    open fun handleError(throwable: Throwable) {}
}
