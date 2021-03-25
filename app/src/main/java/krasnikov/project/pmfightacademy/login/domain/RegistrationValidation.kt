package krasnikov.project.pmfightacademy.login.domain

import krasnikov.project.pmfightacademy.login.data.model.Login
import krasnikov.project.pmfightacademy.login.data.model.Register
import javax.inject.Inject

class RegistrationValidation @Inject constructor(
    private val register: Register,
) {
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