package krasnikov.project.pmfightacademy.auth.login.domain

import krasnikov.project.pmfightacademy.app.domain.ResolveGeneralErrorUseCase
import krasnikov.project.pmfightacademy.auth.domain.verfication.exceptions.PasswordNotValidException
import krasnikov.project.pmfightacademy.auth.domain.verfication.exceptions.PhoneNotValidException
import krasnikov.project.pmfightacademy.utils.ErrorWrapper
import javax.inject.Inject

class ResolveLoginErrorUseCase @Inject constructor(
    private val resolveGeneralErrorUseCase: ResolveGeneralErrorUseCase
) {

    fun execute(throwable: Throwable): ErrorWrapper<LoginError> {
        return when (throwable) {
            is PhoneNotValidException -> {
                ErrorWrapper.ClassSpecific(LoginError.UserPhoneInvalid)
            }
            is PasswordNotValidException -> {
                ErrorWrapper.ClassSpecific(LoginError.UserPasswordInvalid)
            }
            else -> {
                resolveGeneralErrorUseCase.execute(throwable)
            }
        }
    }
}
