package krasnikov.project.pmfightacademy.auth.login.domain

import androidx.annotation.StringRes
import krasnikov.project.pmfightacademy.R

enum class LoginError(@StringRes val errorString: Int) {
    UserPhoneInvalid(R.string.error_phone_invalid),
    UserPasswordInvalid(R.string.error_password_invalid)
}
