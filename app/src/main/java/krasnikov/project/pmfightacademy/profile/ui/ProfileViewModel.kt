package krasnikov.project.pmfightacademy.profile.ui

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import krasnikov.project.pmfightacademy.LoginFlowDirections
import krasnikov.project.pmfightacademy.app.data.pref.SharedPref
import krasnikov.project.pmfightacademy.app.ui.base.BaseViewModel
import krasnikov.project.pmfightacademy.notifications.FirebaseMessagingManager
import krasnikov.project.pmfightacademy.utils.Event
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val firebaseMessagingManager: FirebaseMessagingManager
) :
    BaseViewModel() {

    fun enableNotifications() {
        viewModelScope.launch(exceptionHandler) {
            firebaseMessagingManager.subscribeToMessages()
        }
    }

    fun disableNotifications() {
        viewModelScope.launch(exceptionHandler) {
            firebaseMessagingManager.unsubscribeFromMessages()
        }
    }

    fun logOut() {
        viewModelScope.launch(exceptionHandler) {
            eventChannel.send(
                Event.Navigation(LoginFlowDirections.actionGlobalToLogin())
            )
        }
    }

}
