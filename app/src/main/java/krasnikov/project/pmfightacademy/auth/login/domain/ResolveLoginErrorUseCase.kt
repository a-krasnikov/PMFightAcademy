package krasnikov.project.pmfightacademy.auth.login.domain

import krasnikov.project.pmfightacademy.auth.domain.verfication.exceptions.PasswordNotValidException
import krasnikov.project.pmfightacademy.auth.domain.verfication.exceptions.PhoneNotValidException
import javax.inject.Inject

class ResolveLoginErrorUseCase @Inject constructor() {

    fun execute(throwable: Throwable): LoginError {
        return when (throwable) {
            is PhoneNotValidException -> {
                LoginError.UserPhoneInvalid
            }
            is PasswordNotValidException -> {
                LoginError.UserPasswordInvalid
            }
            // is http 409
            else -> {
                LoginError.UnknownError
            }
        }
    }
}
