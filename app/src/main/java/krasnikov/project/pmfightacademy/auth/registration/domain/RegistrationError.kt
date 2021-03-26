package krasnikov.project.pmfightacademy.auth.registration.domain

import androidx.annotation.StringRes
import krasnikov.project.pmfightacademy.R

enum class RegistrationError(@StringRes val errorString: Int) {
    //Validation
    UserPhoneInvalid(R.string.phoneInvalid),
    UserPasswordInvalid(R.string.passwordInvalid),
    UserNameInvalid(0),
    //Network
    InvalidUserDataSent(0),
    SuchUserAlreadyExits(0),
    //Unknown
    UnknownError(0)
}
