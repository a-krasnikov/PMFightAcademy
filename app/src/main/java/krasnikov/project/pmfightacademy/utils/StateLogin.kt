package krasnikov.project.pmfightacademy.utils

import krasnikov.project.pmfightacademy.auth.data.model.AccessToken
import krasnikov.project.pmfightacademy.auth.login.domain.LoginValidationError

sealed class StateLogin {
    object Loading : StateLogin()
    data class Success(val accessToken: AccessToken) : StateLogin()
    data class ValidationError(val validationError: LoginValidationError) : StateLogin()
    object DataError : StateLogin()
}
