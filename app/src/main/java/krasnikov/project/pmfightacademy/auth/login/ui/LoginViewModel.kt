package krasnikov.project.pmfightacademy.auth.login.ui

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import krasnikov.project.pmfightacademy.app.data.pref.SharedPref
import krasnikov.project.pmfightacademy.app.ui.base.BaseViewModel
import krasnikov.project.pmfightacademy.auth.data.model.Login
import krasnikov.project.pmfightacademy.auth.login.domain.LoginError
import krasnikov.project.pmfightacademy.auth.login.domain.LoginUserUseCase
import krasnikov.project.pmfightacademy.auth.login.domain.ResolveLoginErrorUseCase
import krasnikov.project.pmfightacademy.utils.ErrorWrapper
import krasnikov.project.pmfightacademy.utils.Event
import krasnikov.project.pmfightacademy.utils.State
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase,
    private val resolveLoginErrorUseCase: ResolveLoginErrorUseCase,
    private val pref: SharedPref,
) : BaseViewModel() {

    fun login(phone: String, password: String): Flow<State<Unit, ErrorWrapper<LoginError>>> {
        return flow<State<Unit, ErrorWrapper<LoginError>>> {
            emit(State.Loading)
            pref.token = loginUserUseCase.execute(Login(phone, password)).token
            emit(State.Content(Unit))
        }.catch { exception ->
            emit(State.Error(resolveLoginErrorUseCase.execute(exception)))
        }
    }

    fun navigateToMainContent() {
        viewModelScope.launch {
            eventChannel.send(
                Event.Navigation(LoginFragmentDirections.actionLoginToMainContent())
            )
        }
    }

    fun navigateToRegistration() {
        viewModelScope.launch {
            eventChannel.send(
                Event.Navigation(LoginFragmentDirections.actionLoginToRegister())
            )
        }
    }
}
