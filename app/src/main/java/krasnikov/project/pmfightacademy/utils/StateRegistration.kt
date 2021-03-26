package krasnikov.project.pmfightacademy.utils

import krasnikov.project.pmfightacademy.auth.data.model.AccessToken
import krasnikov.project.pmfightacademy.auth.registration.domain.RegistrationValidationError

sealed class StateRegistration {
    object Loading : StateRegistration()
    data class Success(val accessToken: AccessToken) : StateRegistration()
    data class ValidationError(val validationError: RegistrationValidationError) : StateRegistration()
    object DataError : StateRegistration()
}
