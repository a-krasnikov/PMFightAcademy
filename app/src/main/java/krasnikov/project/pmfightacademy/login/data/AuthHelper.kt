package krasnikov.project.pmfightacademy.login.data

import android.net.Uri
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import krasnikov.project.pmfightacademy.login.data.model.AccessToken
import retrofit2.Response
import javax.inject.Inject

class AuthHelper @Inject constructor(private val loginService: LoginService) {

//    private companion object {
//        const val SCHEMA = "https"
//
//        /** Заменить "github.com" на нужную точку входа*/
//        const val HOST = "github.com"
//
////        const val LOGIN_PATH = "/Clients/Login"
//    }

//    val fightAcademyUrl: Uri
//        get() = Uri.Builder()
//            .scheme(SCHEMA)
//            .authority(HOST)
////            .appendEncodedPath(LOGIN_PATH)
////            .appendQueryParameter("client_id", CLIENT_ID)
////            .appendQueryParameter("scope", OAUTH2_SCOPE)
////            .appendQueryParameter("redirect_url", REDIRECT_URL)
//            .build()


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