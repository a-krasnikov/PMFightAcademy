package krasnikov.project.pmfightacademy.auth.registration.domain

import androidx.annotation.StringRes
import krasnikov.project.pmfightacademy.R

enum class RegistrationError(@StringRes val errorString: Int) {
    UserPhoneInvalid(R.string.error_phone_invalid),
    UserPasswordInvalid(R.string.error_password_invalid),
    UserNameInvalid(R.string.error_name_invalid)
}
