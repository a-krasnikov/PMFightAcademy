package krasnikov.project.pmfightacademy.auth.registration.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import krasnikov.project.pmfightacademy.app.di.DefaultDispatcher
import krasnikov.project.pmfightacademy.auth.data.AuthRepository
import krasnikov.project.pmfightacademy.auth.data.model.AccessToken
import krasnikov.project.pmfightacademy.auth.data.model.Register
import krasnikov.project.pmfightacademy.auth.domain.verfication.AuthValidator
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val registrationValidator: AuthValidator,
    private val authRepository: AuthRepository,
    @DefaultDispatcher
    private val defaultDispatcher: CoroutineDispatcher
) {

    suspend fun execute(registrationModel: Register): AccessToken {
        return withContext(defaultDispatcher) {
            registrationValidator.validatePhone(registrationModel.phone)
            registrationValidator.validatePassword(registrationModel.password)
            registrationValidator.validateName(registrationModel.name)

            authRepository.register(registrationModel)
        }
    }

}
