package krasnikov.project.pmfightacademy.login.registation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import krasnikov.project.pmfightacademy.app.base.BaseViewModel
import krasnikov.project.pmfightacademy.app.data.pref.SharedPref
import krasnikov.project.pmfightacademy.login.data.LoginRepository
import krasnikov.project.pmfightacademy.utils.ErrorType
import krasnikov.project.pmfightacademy.utils.State
import javax.inject.Inject
@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val authHelper: LoginRepository,
    private val pref: SharedPref,
) : BaseViewModel() {

    private val _content = MutableLiveData<State<Unit, ErrorType>>()
    val content
        get() = _content as LiveData<State<Unit, ErrorType>>

    fun startRegistration(context: Context?, login: String, password: String, name: String) {
//        if (ValidationPost(login, password, name).getRegisterValidation()) {
//            baseViewModelScope.launch() {
//                _content.postValue(State.Loading)
//                val result = authHelper.getNewRegistration(login = login, password = password, name=name)
//                pref.token = "${result.body()?.accessToken}"
//                _content.postValue(State.Content(Unit))
//                navigateAcademyInfo()
//            }
//        } else {
//            Log.d("TestLog", "ValidationPost(phone,pass).invoke()")
//            Toast.makeText(context, "Wrong phone or password", Toast.LENGTH_LONG).show()
//        }
    }

    private fun navigateAcademyInfo() {
//        _navigationEvent.postValue(NavigationEvent {
//            Navigator.navigateToAcademyInfo(
//                it
//            )
//        })
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