package krasnikov.project.pmfightacademy.login.domain

import android.util.Log
import krasnikov.project.pmfightacademy.login.data.model.Login
import krasnikov.project.pmfightacademy.login.data.model.Register
import javax.inject.Inject

@Suppress("MatchingDeclarationName")
class ValidationLogin @Inject constructor(
    private val login: Login,
){
    fun getLoginValidation(): Boolean {
        Log.d("21","121")
        /*   if (login.length != 10) {
               Log.d("TestLog", "(phone.length != 7)")
               return false
           } else if (password.length < 5) {
               Log.d("TestLog", "(pass.length < 5)")
               return false
           }*/
        return true
    }
}
