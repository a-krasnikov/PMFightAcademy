package krasnikov.project.pmfightacademy.auth.registration.domain

import javax.inject.Inject

class RegistrationValidator @Inject constructor() {
    fun isPhoneValid(phone: String): Boolean {
        return phoneRegex.matches(phone)
    }

    fun isPasswordValid(password: String): Boolean {
        return passwordRegex.matches(password)
    }

    fun isNameValid(name: String) : Boolean {
        return name.length >= NAME_MINIMUM_LENGTH
    }

    private companion object {
        val passwordRegex = Regex("(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z]).{8,}")
        val phoneRegex = Regex("^\\+380[5-9][0-9]\\d{7}\$")
        const val NAME_MINIMUM_LENGTH = 2
    }
}
