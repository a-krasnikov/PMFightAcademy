package krasnikov.project.pmfightacademy.app.data.interceptors

import krasnikov.project.pmfightacademy.app.data.pref.SharedPref
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(private val sharedPref: SharedPref) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.request()
        val token = sharedPref.token

        val authRequest = response.newBuilder().addHeader(
            "Authorization",
            "bearer $token"
        ).build()

        return chain.proceed(authRequest)
    }
}
