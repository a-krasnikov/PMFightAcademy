package krasnikov.project.pmfightacademy.login.domain

import krasnikov.project.pmfightacademy.login.data.model.Login
import krasnikov.project.pmfightacademy.login.data.model.Register
import javax.inject.Inject

class ValidationLogin @Inject constructor(
    private val login: Login,
){


    fun getLoginValidation(): Boolean {
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
