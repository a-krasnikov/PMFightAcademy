package krasnikov.project.pmfightacademy.auth.login.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import krasnikov.project.pmfightacademy.app.di.DefaultDispatcher
import krasnikov.project.pmfightacademy.auth.data.AuthRepository
import krasnikov.project.pmfightacademy.auth.data.model.AccessToken
import krasnikov.project.pmfightacademy.auth.data.model.Login
import krasnikov.project.pmfightacademy.auth.domain.verfication.AuthValidator
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val loginValidator: AuthValidator,
    private val authRepository: AuthRepository,
    @DefaultDispatcher
    private val defaultDispatcher: CoroutineDispatcher
) {

    suspend fun execute(loginModel: Login): AccessToken {
        return withContext(defaultDispatcher) {
            loginValidator.validatePhone(loginModel.phone)
            loginValidator.validatePassword(loginModel.password)

            authRepository.login(loginModel)
        }
    }
}
