package krasnikov.project.pmfightacademy.auth.login.domain

import javax.inject.Inject

class LoginValidator @Inject constructor() {

    fun isPhoneValid(phone: String): Boolean {
        return phoneRegex.matches(phone)
    }

    fun isPasswordValid(password: String): Boolean {
        return passwordRegex.matches(password)
    }

    private companion object {
        val passwordRegex = Regex("(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z]).{8,}")
        val phoneRegex = Regex("^\\+380[5-9][0-9]\\d{7}\$")
    }
}
