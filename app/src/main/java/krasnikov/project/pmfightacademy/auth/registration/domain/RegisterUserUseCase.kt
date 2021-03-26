package krasnikov.project.pmfightacademy.auth.registration.domain

import krasnikov.project.pmfightacademy.auth.data.AuthRepository
import krasnikov.project.pmfightacademy.auth.data.model.Register
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val registrationValidator: RegistrationValidator,
    private val authRepository: AuthRepository
) {

    suspend fun execute(registrationModel: Register): RegistrationResult {
        val phoneValid = registrationValidator.isPhoneValid(registrationModel.phone)
        val passwordValid = registrationValidator.isPasswordValid(registrationModel.password)
        val nameValid = registrationValidator.isNameValid(registrationModel.name)

        return if (!phoneValid && !passwordValid && !nameValid) {
            RegistrationResult.RegistrationError(RegistrationValidationError.UserPhoneNameAndPasswordInvalid)
        } else if (!passwordValid) {
            RegistrationResult.RegistrationError(RegistrationValidationError.UserPasswordInvalid)
        } else if (!phoneValid) {
            RegistrationResult.RegistrationError(RegistrationValidationError.UserPhoneInvalid)
        } else if (!nameValid) {
            RegistrationResult.RegistrationError(RegistrationValidationError.UserNameInvalid)
        } else {
            RegistrationResult.RegistrationSuccess(authRepository.register(registrationModel))
        }
    }

}
