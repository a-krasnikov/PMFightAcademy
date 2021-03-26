package krasnikov.project.pmfightacademy.auth.login.domain

import krasnikov.project.pmfightacademy.auth.data.model.AccessToken

sealed class LoginResult {
    data class LoginSuccess(val token: AccessToken) : LoginResult()
    data class LoginError(val error: LoginValidationError) : LoginResult()
}
