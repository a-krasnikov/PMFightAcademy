package krasnikov.project.pmfightacademy.login.data

import android.net.Uri
import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import krasnikov.project.pmfightacademy.app.di.IoDispatcher
import krasnikov.project.pmfightacademy.login.data.model.AccessToken
import krasnikov.project.pmfightacademy.login.data.model.Login
import krasnikov.project.pmfightacademy.login.data.model.Register
import retrofit2.Response
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val loginService: LoginService,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher,
) {

    suspend fun getAccessToken(login: Login): AccessToken {
        Log.d("LOGINLOG", "LoginRepository -> getAccessToken()")
        return withContext(ioDispatcher) {
            loginService.getAccessToken(login)
        }
    }

    suspend fun getNewRegistration(register: Register): AccessToken {
        Log.d("LOGINLOG", "LoginRepository -> getNewRegistration()")
        return withContext(ioDispatcher) {
            loginService.getNewRegistration(register)
        }
    }
}