package krasnikov.project.pmfightacademy.auth.login.domain

import androidx.annotation.StringRes
import krasnikov.project.pmfightacademy.R

enum class LoginValidationError(@StringRes val errorString: Int) {
    UserPhoneInvalid(R.string.phoneInvalid),
    UserPasswordInvalid(R.string.passwordInvalid),
    UserPhoneAndPasswordInvalid(R.string.phoneAndPasswordInvalid)
}
