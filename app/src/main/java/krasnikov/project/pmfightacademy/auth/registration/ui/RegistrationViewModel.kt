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
import krasnikov.project.pmfightacademy.auth.registration.domain.RegistrationResult
import krasnikov.project.pmfightacademy.utils.Event
import krasnikov.project.pmfightacademy.utils.StateRegistration
import javax.inject.Inject

@Suppress("EmptyFunctionBlock")
@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase,
    private val pref: SharedPref,
) : BaseViewModel() {

    fun startRegistration(phone: String, password: String, name: String): Flow<StateRegistration> {
        return flow {
            emit(StateRegistration.Loading)
            val registrationResult = registerUserUseCase.execute(
                Register(
                    phone = phone,
                    password = password,
                    name = name
                )
            )

            when(registrationResult) {
                is RegistrationResult.RegistrationSuccess -> {
                    pref.token = registrationResult.token.token
                    emit(StateRegistration.Success(registrationResult.token))
                }
                is RegistrationResult.RegistrationError -> {
                    emit(StateRegistration.ValidationError(registrationResult.error))
                    eventChannel.send(Event.Message(registrationResult.error.errorString))
                }
            }
            /*if (RegistrationValidation(register).getGeneralRegistrationValidation(register)) {
                emit(StateLogin.Loading)
                val token = authRepository.register(register)
                pref.token = token.toString()
                emit(StateLogin.Success(token))
                //TODO -> подставить правильный фрамент
                //navigateAcademyInfo()
            } else {
                Log.d("LOGINLOG", "LoginViewModel -> getAccessToken() -> else")
                emit(StateLogin.Error(ErrorType.UserNotIdentified))
            }*/
        }.catch { exception ->
            emit(StateRegistration.DataError)
            handleError(exception)
        }
    }

    fun navigateToMainContent() {
        viewModelScope.launch {
            eventChannel.send(Event.Navigation(RegistrationFragmentDirections.actionRegisterToMainContent())
            )
        }
    }

    override fun handleError(throwable: Throwable) {
    }
}
