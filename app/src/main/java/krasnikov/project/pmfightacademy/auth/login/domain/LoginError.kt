package krasnikov.project.pmfightacademy.auth.login.domain

import androidx.annotation.StringRes
import krasnikov.project.pmfightacademy.R

enum class LoginError(@StringRes val errorString: Int) {
    //Validation
    UserPhoneInvalid(R.string.phoneInvalid),
    UserPasswordInvalid(R.string.passwordInvalid),
    //Network
    InvalidUserDataSent(0),
    //Unknown
    UnknownError(0)
}
