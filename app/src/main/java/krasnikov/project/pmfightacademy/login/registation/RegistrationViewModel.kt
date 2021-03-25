package krasnikov.project.pmfightacademy.login.registation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import krasnikov.project.pmfightacademy.app.base.BaseViewModel
import krasnikov.project.pmfightacademy.app.data.pref.SharedPref
import krasnikov.project.pmfightacademy.login.data.LoginRepository
import krasnikov.project.pmfightacademy.login.data.model.Register
import krasnikov.project.pmfightacademy.login.domain.RegistrationValidation
import krasnikov.project.pmfightacademy.login.domain.ValidationLogin
import krasnikov.project.pmfightacademy.utils.ErrorType
import krasnikov.project.pmfightacademy.utils.State
import krasnikov.project.pmfightacademy.utils.StateLogin
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val pref: SharedPref,
) : BaseViewModel() {

    fun startRegistration(register: Register): Flow<StateLogin<ErrorType>> {
        Log.d("LOGINLOG", "LoginViewModel -> startRegistration()")
        return flow {
            if (RegistrationValidation(register).getRegisterValidation()) {
                Log.d("LOGINLOG", "LoginViewModel -> startRegistration() -> if")
                emit(StateLogin.Loading)
                Log.d("LOGINLOG", "1 token ${pref.token}")
                val token = loginRepository.getNewRegistration(register)
                pref.token = token.toString()
                Log.d("LOGINLOG", "token ${pref.token}")
                emit(StateLogin.Success(token))
                //navigateAcademyInfo()
            } else {
                Log.d("LOGINLOG", "LoginViewModel -> getAccessToken() -> else")
//                emit(StateLogin.Error(ErrorType.UserNotIdentified))
            }
        }.catch {
            //
        }

    }

    override fun handleError(throwable: Throwable) {
//        super.handleError(throwable)
//        when (throwable) {
//            is RequestNotAuthorizedException -> {
//                _content.postValue(State.Error(ErrorType.UserNotIdentified))
//            }
//            is NetworkRequestException -> {
//                _content.postValue(State.Error(ErrorType.NetworkProblem))
//            }
//            else -> {
//                _content.postValue(State.Error(ErrorType.UnknownError))
//            }
//        }
    }
}