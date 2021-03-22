package krasnikov.project.pmfightacademy.login.data

import android.net.Uri
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import krasnikov.project.pmfightacademy.login.data.model.AccessToken
import retrofit2.Response
import javax.inject.Inject

class AuthHelper @Inject constructor(private val loginService: LoginService) {

    suspend fun getAccessToken(login: String, password:String): Response<AccessToken> {
        return withContext(Dispatchers.IO) {
            loginService.getAccessToken(login, password)
        }
    }

    suspend fun getNewRegistration(login: String, password:String, name:String): Response<AccessToken> {
        return withContext(Dispatchers.IO) {
            loginService.getNewRegistration(login, password, name)
        }
    }
}