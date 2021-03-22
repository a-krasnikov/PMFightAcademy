package krasnikov.project.pmfightacademy.launch.ui

import krasnikov.project.pmfightacademy.app.base.BaseViewModel
import krasnikov.project.pmfightacademy.app.data.pref.SharedPref
import krasnikov.project.pmfightacademy.app.navigation.NavigationEvent
import krasnikov.project.pmfightacademy.app.navigation.Navigator
import javax.inject.Inject

class LaunchViewModel @Inject constructor(
    private val pref: SharedPref,
) : BaseViewModel() {

    fun checkSharedPref() {
        if (pref.token.isEmpty()) {
            navigateLoginFragmet()
        } else{
            navigateToAcademyInfo()
        }
    }

    private fun navigateLoginFragmet() {
        _navigationEvent.postValue(NavigationEvent {
            Navigator.navigateToLogin(it)
        })
    }

    private fun navigateToAcademyInfo() {
        _navigationEvent.postValue(NavigationEvent {
            Navigator.navigateToLogin(it)
        })
    }


}
