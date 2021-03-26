package krasnikov.project.pmfightacademy.auth.login.domain

import krasnikov.project.pmfightacademy.auth.data.AuthRepository
import krasnikov.project.pmfightacademy.auth.data.model.Login
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val loginValidator: LoginValidator,
    private val authRepository: AuthRepository
) {

    suspend fun execute(loginModel: Login): LoginResult {
        val phoneValid = loginValidator.isPhoneValid(loginModel.phone)
        val passwordValid = loginValidator.isPasswordValid(loginModel.password)

        return if (!phoneValid && !passwordValid) {
            LoginResult.LoginError(LoginValidationError.UserPhoneAndPasswordInvalid)
        } else if (!passwordValid) {
            LoginResult.LoginError(LoginValidationError.UserPasswordInvalid)
        } else if (!phoneValid) {
            LoginResult.LoginError(LoginValidationError.UserPhoneInvalid)
        } else {
            LoginResult.LoginSuccess(authRepository.login(loginModel))
        }
    }
}
