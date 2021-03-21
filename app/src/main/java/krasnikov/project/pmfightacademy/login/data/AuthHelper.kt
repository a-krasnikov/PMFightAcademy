package krasnikov.project.pmfightacademy.login.data

import android.net.Uri
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import krasnikov.project.pmfightacademy.login.data.model.AccessToken
import javax.inject.Inject

class AuthHelper @Inject constructor(private val loginService: LoginService) {

    private companion object {
        const val SCHEMA = "https"

        /** Заменить "github.com" на нужную точку входа*/
        const val HOST = "github.com"

        /** Уточнить нежун ли REDIRECT_URL*/
        const val REDIRECT_URL = "pmgithubclient://callback"

        const val LOGIN_PATH = "/Clients/Login"
    }

    val fightAcademyUrl: Uri
        get() = Uri.Builder()
            .scheme(SCHEMA)
            .authority(HOST)
            .appendEncodedPath(LOGIN_PATH)
//            .appendQueryParameter("client_id", CLIENT_ID)
//            .appendQueryParameter("scope", OAUTH2_SCOPE)
//            .appendQueryParameter("redirect_url", REDIRECT_URL)
            .build()

    fun getCodeFromUri(uri: Uri?): String? {
        uri ?: return null
        if (!uri.toString().startsWith(REDIRECT_URL)) {
            return null
        }
        return uri.getQueryParameter("code")
    }

    suspend fun getAccessToken(login: String, pass:String): AccessToken {
        return withContext(Dispatchers.IO) {
            loginService.getAccessToken(login, pass)
        }
    }
}