package krasnikov.project.pmfightacademy.login

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.launch
import krasnikov.project.pmfightacademy.app.BaseViewModel
import krasnikov.project.pmfightacademy.app.data.exception.NetworkRequestException
import krasnikov.project.pmfightacademy.app.data.exception.RequestNotAuthorizedException
import krasnikov.project.pmfightacademy.login.data.AuthHelper
import krasnikov.project.pmfightacademy.app.data.exception.pref.SharedPref
import krasnikov.project.pmfightacademy.utils.ErrorType
import krasnikov.project.pmfightacademy.utils.State
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authHelper: AuthHelper,
    private val pref: SharedPref
) : BaseViewModel() {

    private val _content = MutableLiveData<State<Unit, ErrorType>>()
    val content
        get() = _content as LiveData<State<Unit, ErrorType>>

    val authGitHubUrl
        get() = authHelper.fightAcademyUrl

    fun handleOauth(intent: Intent) {
        intent.data?.let { it ->
            authHelper.getCodeFromUri(it)?.let { code ->
                getAccessToken(code)
            }
        }
    }

/*    private fun navigateToUserInfo() {
        _navigationEvent.postValue(NavigationEvent {
            Navigator.navigateToUserInfo(
                it,
                UserProfile.LoggedUser
            )
        })
    }*/

    private fun getAccessToken(login: String, pass:String) {
        baseViewModelScope.launch() {
            _content.postValue(State.Loading)
            val result = authHelper.getAccessToken(login = login, pass = pass)
            pref.token = "${result.accessToken}"
            _content.postValue(State.Content(Unit))
            navigateToUserInfo()
        }
    }

    override fun handleError(throwable: Throwable, coroutineName: CoroutineName?) {
        super.handleError(throwable, coroutineName)
        when (throwable) {
            is RequestNotAuthorizedException -> {
                _content.postValue(State.Error(ErrorType.UserNotIdentified))
            }
            is NetworkRequestException -> {
                _content.postValue(State.Error(ErrorType.NetworkProblem))
            }
            else -> {
                _content.postValue(State.Error(ErrorType.UnknownError))
            }
        }
    }
}