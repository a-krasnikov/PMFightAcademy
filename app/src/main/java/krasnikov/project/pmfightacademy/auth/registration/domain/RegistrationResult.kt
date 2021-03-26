package krasnikov.project.pmfightacademy.auth.registration.domain

import krasnikov.project.pmfightacademy.auth.data.model.AccessToken

sealed class RegistrationResult {
    data class RegistrationSuccess(val token: AccessToken) : RegistrationResult()
    data class RegistrationError(val error: RegistrationValidationError) : RegistrationResult()
}
