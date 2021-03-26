package krasnikov.project.pmfightacademy.auth.registration.domain

import krasnikov.project.pmfightacademy.auth.domain.verfication.exceptions.NameNotValidException
import krasnikov.project.pmfightacademy.auth.domain.verfication.exceptions.PasswordNotValidException
import krasnikov.project.pmfightacademy.auth.domain.verfication.exceptions.PhoneNotValidException
import javax.inject.Inject

class ResolveRegistrationErrorUseCase @Inject constructor() {

    fun execute(throwable: Throwable): RegistrationError {
        return when (throwable) {
            is PhoneNotValidException -> {
                RegistrationError.UserPhoneInvalid
            }
            is PasswordNotValidException -> {
                RegistrationError.UserPasswordInvalid
            }
            is NameNotValidException -> {
                RegistrationError.UserNameInvalid
            }
            // is http 400
            // is http 409
            else -> {
                RegistrationError.UnknownError
            }
        }
    }
}