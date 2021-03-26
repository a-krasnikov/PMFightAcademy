package krasnikov.project.pmfightacademy.auth.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import krasnikov.project.pmfightacademy.app.di.IoDispatcher
import krasnikov.project.pmfightacademy.auth.data.model.AccessToken
import krasnikov.project.pmfightacademy.auth.data.model.Login
import krasnikov.project.pmfightacademy.auth.data.model.Register
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authService: AuthService,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher,
) {

    suspend fun login(login: Login): AccessToken {
        return withContext(ioDispatcher) {
            authService.login(login)
        }
    }

    suspend fun register(register: Register): AccessToken {
        return withContext(ioDispatcher) {
            authService.register(register)
        }
    }
}
