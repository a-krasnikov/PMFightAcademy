package krasnikov.project.pmfightacademy.login.registation

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import krasnikov.project.pmfightacademy.app.base.BaseViewModel
import krasnikov.project.pmfightacademy.app.data.pref.SharedPref
import krasnikov.project.pmfightacademy.login.data.LoginRepository
import krasnikov.project.pmfightacademy.login.data.model.Register
import krasnikov.project.pmfightacademy.login.domain.RegistrationValidation
import krasnikov.project.pmfightacademy.login.registation.ui.RegistrationFragmentDirections
import krasnikov.project.pmfightacademy.utils.ErrorType
import krasnikov.project.pmfightacademy.utils.Event
import krasnikov.project.pmfightacademy.utils.StateLogin
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val pref: SharedPref,
) : BaseViewModel() {

    fun startRegistration(register: Register): Flow<StateLogin<ErrorType>> {
        return flow {
            if (RegistrationValidation(register).getRegisterValidation(register)) {
                emit(StateLogin.Loading)
                val token = loginRepository.getNewRegistration(register)
                pref.token = token.toString()
                emit(StateLogin.Success(token))
                //TODO -> подставить правильный фрамент
                //navigateAcademyInfo()
            } else {
                Log.d("LOGINLOG", "LoginViewModel -> getAccessToken() -> else")
                emit(StateLogin.Error(ErrorType.UserNotIdentified))
            }
        }.catch {
            //TODO -> закончить catch
            Log.d("LOGINLOG", "LoginViewModel -> getAccessToken() -> else")
        }
    }

    private fun navigateAcademyInfo() {
        viewModelScope.launch {
            eventChannel.send(Event.Navigation(RegistrationFragmentDirections.actionRegisterToMainContent())
            )
        }
    }

    override fun handleError(throwable: Throwable) {
    }
}