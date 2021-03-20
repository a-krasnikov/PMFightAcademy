package krasnikov.project.pmfightacademy.login

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineName
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
        get() = authHelper.authGitHubUrl

    fun handleOauth(intent: Intent) {
        intent.data?.let { it ->
            authHelper.getCodeFromUri(it)?.let { code ->
                getAccessToken(code)
            }
        }
    }

    private fun navigateToUserInfo() {
        _navigationEvent.postValue(NavigationEvent {
            Navigator.navigateToUserInfo(
                it,
                UserProfile.LoggedUser
            )
        })
    }

    private fun getAccessToken(code: String) {
        baseViewModelScope.launch() {
            _content.postValue(State.Loading)
            val result = authHelper.getAccessToken(code)
            pref.token = "${result.tokenType} ${result.accessToken}"
            _content.postValue(State.Content(Unit))
            navigateToUserInfo()
        }
    }

    override fun handleError(throwable: Throwable, coroutineName: CoroutineName?) {
        super.handleError(throwable, coroutineName)
        when (throwable) {
            is NetworkRequestException -> {
                _content.postValue(State.Error(ErrorType.AccessTokenError))
            }
            else -> {
                _content.postValue(State.Error(ErrorType.UnknownError))
            }
        }
    }
}