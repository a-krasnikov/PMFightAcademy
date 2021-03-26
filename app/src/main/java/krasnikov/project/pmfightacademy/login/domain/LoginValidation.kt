package krasnikov.project.pmfightacademy.login.domain

import android.util.Log
import krasnikov.project.pmfightacademy.login.data.model.Login
import krasnikov.project.pmfightacademy.login.data.model.Register
import javax.annotation.RegEx
import javax.inject.Inject

@Suppress("MatchingDeclarationName")
class ValidationLogin @Inject constructor(
    private val login: Login,
) {

    fun getGeneralLoginValidation(login: Login): Boolean {
        return getLoginValidation(login) && getPasswordValidation(login)
    }

    fun getLoginValidation(login: Login): Boolean {
        val validationLogin = login.login.toString()
        var patternLogin = Regex("\\\\+380[5-9][0-9]\\\\d{7}\$")
        return (patternLogin.containsMatchIn(validationLogin))
//        return true
    }

    fun getPasswordValidation(login: Login): Boolean {
        val validationPassword = login.password.toString()
        var patternLogin = Regex("(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z]).{8,}")
        return (patternLogin.containsMatchIn(validationPassword))
//        return true
    }
}
