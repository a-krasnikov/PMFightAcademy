package krasnikov.project.pmfightacademy.auth.registration.domain

import androidx.annotation.StringRes
import krasnikov.project.pmfightacademy.R

enum class RegistrationValidationError(@StringRes val errorString: Int) {
    UserPhoneInvalid(R.string.phoneInvalid),
    UserPasswordInvalid(R.string.passwordInvalid),
    UserNameInvalid(0),
    UserPhoneNameAndPasswordInvalid(0)
}
