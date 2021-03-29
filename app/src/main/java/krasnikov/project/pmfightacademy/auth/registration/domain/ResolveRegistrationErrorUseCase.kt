package krasnikov.project.pmfightacademy.auth.registration.domain

import krasnikov.project.pmfightacademy.app.domain.ResolveGeneralErrorUseCase
import krasnikov.project.pmfightacademy.auth.domain.verfication.exceptions.NameNotValidException
import krasnikov.project.pmfightacademy.auth.domain.verfication.exceptions.PasswordNotValidException
import krasnikov.project.pmfightacademy.auth.domain.verfication.exceptions.PhoneNotValidException
import krasnikov.project.pmfightacademy.utils.ErrorWrapper
import javax.inject.Inject

class ResolveRegistrationErrorUseCase @Inject constructor(
    private val resolveGeneralErrorUseCase: ResolveGeneralErrorUseCase
) {

    fun execute(throwable: Throwable): ErrorWrapper<RegistrationError> {
        return when (throwable) {
            is PhoneNotValidException -> {
                ErrorWrapper.ClassSpecific(RegistrationError.UserPhoneInvalid)
            }
            is PasswordNotValidException -> {
                ErrorWrapper.ClassSpecific(RegistrationError.UserPasswordInvalid)
            }
            is NameNotValidException -> {
                ErrorWrapper.ClassSpecific(RegistrationError.UserNameInvalid)
            }
            else -> {
                resolveGeneralErrorUseCase.execute(throwable)
            }
        }
    }
}
