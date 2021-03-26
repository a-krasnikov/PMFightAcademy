package krasnikov.project.pmfightacademy.auth.registration.ui

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import krasnikov.project.pmfightacademy.app.base.BaseViewModel
import krasnikov.project.pmfightacademy.app.data.pref.SharedPref
import krasnikov.project.pmfightacademy.auth.data.model.Register
import krasnikov.project.pmfightacademy.auth.registration.domain.RegisterUserUseCase
import krasnikov.project.pmfightacademy.auth.registration.domain.RegistrationError
import krasnikov.project.pmfightacademy.auth.registration.domain.ResolveRegistrationErrorUseCase
import krasnikov.project.pmfightacademy.utils.Event
import krasnikov.project.pmfightacademy.utils.State
import javax.inject.Inject

@Suppress("EmptyFunctionBlock")
@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase,
    private val resolveRegistrationErrorUseCase: ResolveRegistrationErrorUseCase,
    private val pref: SharedPref,
) : BaseViewModel() {

    fun startRegistration(phone: String, password: String, name: String): Flow<State<Unit, RegistrationError>> {
        return flow<State<Unit, RegistrationError>> {
            emit(State.Loading)
            pref.token = registerUserUseCase.execute(Register(phone, password, name)).token
            emit(State.Content(Unit))

        }.catch { exception ->
            emit(State.Error(resolveRegistrationErrorUseCase.execute(exception)))
        }
    }

    fun navigateToMainContent() {
        viewModelScope.launch {
            eventChannel.send(Event.Navigation(RegistrationFragmentDirections.actionRegisterToMainContent())
            )
        }
    }
}
