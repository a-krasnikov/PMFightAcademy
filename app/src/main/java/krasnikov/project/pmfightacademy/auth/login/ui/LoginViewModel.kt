package krasnikov.project.pmfightacademy.auth.login.ui

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import krasnikov.project.pmfightacademy.app.base.BaseViewModel
import krasnikov.project.pmfightacademy.app.data.pref.SharedPref
import krasnikov.project.pmfightacademy.auth.data.model.Login
import krasnikov.project.pmfightacademy.auth.login.domain.LoginResult
import krasnikov.project.pmfightacademy.auth.login.domain.LoginUserUseCase
import krasnikov.project.pmfightacademy.utils.Event
import krasnikov.project.pmfightacademy.utils.StateLogin
import javax.inject.Inject

@Suppress("EmptyFunctionBlock")
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase,
    private val pref: SharedPref,
) : BaseViewModel() {

    fun login(phone: String, password: String): Flow<StateLogin> {
        return flow {
            emit(StateLogin.Loading)
            val loginResult = loginUserUseCase.execute(
                Login(
                    phone = phone,
                    password = password
                )
            )

            when (loginResult) {
                is LoginResult.LoginSuccess -> {
                    pref.token = loginResult.token.token
                    emit(StateLogin.Success(loginResult.token))
                }
                is LoginResult.LoginError -> {
                    emit(StateLogin.ValidationError(loginResult.error))
                    eventChannel.send(Event.Message(loginResult.error.errorString))
                }
            }
        }.catch { exception ->
            emit(StateLogin.DataError)
            handleError(exception)
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


    override fun handleError(throwable: Throwable) {

    }
}
