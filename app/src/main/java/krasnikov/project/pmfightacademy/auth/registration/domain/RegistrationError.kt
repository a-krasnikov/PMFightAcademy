package krasnikov.project.pmfightacademy.auth.registration.domain

import androidx.annotation.StringRes
import krasnikov.project.pmfightacademy.R

enum class RegistrationError(@StringRes val errorString: Int) {
    //Validation
    UserPhoneInvalid(R.string.error_phone_invalid),
    UserPasswordInvalid(R.string.error_password_invalid),
    UserNameInvalid(0),
    //Network
    InvalidUserDataSent(0),
    SuchUserAlreadyExits(0),
    //Unknown
    UnknownError(0)
}
