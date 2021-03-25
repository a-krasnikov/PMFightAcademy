package krasnikov.project.pmfightacademy.login

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import krasnikov.project.pmfightacademy.app.base.BaseViewModel
import krasnikov.project.pmfightacademy.login.data.LoginRepository
import krasnikov.project.pmfightacademy.app.data.pref.SharedPref
import krasnikov.project.pmfightacademy.login.data.model.Login
import krasnikov.project.pmfightacademy.login.domain.ValidationLogin
import krasnikov.project.pmfightacademy.login.ui.LoginFragmentDirections
import krasnikov.project.pmfightacademy.utils.ErrorType
import krasnikov.project.pmfightacademy.utils.Event
import krasnikov.project.pmfightacademy.utils.StateLogin
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val pref: SharedPref,
) : BaseViewModel() {

    fun getAccessToken(login: Login): Flow<StateLogin<ErrorType>> {
        return flow {
            if (ValidationLogin(login).getLoginValidation()) {
                Log.d("LOGINLOG", "LoginViewModel -> getAccessToken() -> if")
                emit(StateLogin.Loading)
                Log.d("LOGINLOG", "1 token ${pref.token.toString()}")
                val token = loginRepository.getAccessToken(login)
                Log.d("LOGINLOG", "LoginViewModel -> getAccessToken()")
                pref.token = token.toString()
                Log.d("LOGINLOG", "token ${pref.token}")
                emit(StateLogin.Success(token))
                //TODO -> подставить правильный фрамент
                //navigateAcademyInfo()
            } else {
                Log.d("LOGINLOG", "LoginViewModel -> getAccessToken() -> else")
                emit(StateLogin.Error(ErrorType.UserNotIdentified))
            }
        }.catch {
            //TODO -> закончить catch
            Log.d("LOGINLOG", "LoginViewModel -> getAccessToken() -> catch")
        }
    }

    private fun navigateAcademyInfo() {
        viewModelScope.launch {
            eventChannel.send(Event.Navigation(LoginFragmentDirections.actionLoginToMainContent())
            )
        }
    }

    fun navigateRegistration() {
        viewModelScope.launch {
            eventChannel.send(Event.Navigation(LoginFragmentDirections.actionLoginToRegister())
            )
        }
    }

    // TODO -> убрать showPref()

    fun showPref() {
        Log.d("LOGINLOG", "token  = ${pref.token}")
    }

    // TODO -> перенести cleanPref() на экран настроек
    fun cleanPref() {
        pref.token = ""
        Log.d("LOGINLOG", "LoginViewModel -> cleanPref() token  = ${pref.token}")
    }

    override fun handleError(throwable: Throwable) {
    }
}