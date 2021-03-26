package krasnikov.project.pmfightacademy.login.domain

import android.util.Log
import krasnikov.project.pmfightacademy.login.data.model.Login
import krasnikov.project.pmfightacademy.login.data.model.Register
import javax.inject.Inject

//TODO -> закончить валидацию

class RegistrationValidation @Inject constructor(
    private val register: Register,
) {
    fun getGeneralRegistrationValidation(register: Register): Boolean {
        return getLoginValidation(register) && getPasswordValidation(register)&&getRegisterValidationName(register)
    }

    fun getLoginValidation(register: Register): Boolean {
        val validationLogin = register.login.toString()
        var patternLogin = Regex("^(\\+38|38)?0(39|50|63|66|67|68|91|92|93|94|95|96|97|98|99)\\d{7}\$")
        Log.d("LOGINLOG","RegistrationValidation -> getLoginValidation = ${patternLogin.containsMatchIn(validationLogin)}")
        return (patternLogin.containsMatchIn(validationLogin))
//        return true
    }

    fun getPasswordValidation(register: Register): Boolean {
        val validationPassword = register.password.toString()
        var patternLogin = Regex("(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z]).{8,}")
        Log.d("LOGINLOG","RegistrationValidation -> getPasswordValidation = ${patternLogin.containsMatchIn(validationPassword)}")
        return (patternLogin.containsMatchIn(validationPassword))
//        return true
    }

    private fun getRegisterValidationName(register: Register): Boolean {
        val validationName = register.name.toString()
        return validationName?.length!! >= 2
    }
}