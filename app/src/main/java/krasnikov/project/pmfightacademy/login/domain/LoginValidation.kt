package krasnikov.project.pmfightacademy.login.domain

import android.util.Log
import krasnikov.project.pmfightacademy.login.data.model.Login
import javax.inject.Inject

class ValidationPost @Inject constructor(
    private val login: Login,
) {
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

    fun getRegisterValidation(): Boolean {
        /* val nameLowerCase = name?.toLowerCase()
         if (name != null && nameLowerCase != "name") {
             if (getLoginValidation() && name.length < 2) {
                 Log.d("TestLog", "getLoginValidation() && name.length <2    true")
                 return true
             } else {
                 Log.d("TestLog", "getLoginValidation() && name.length <2    false")
                 return false
             }
         }*/
        return true
    }
}
