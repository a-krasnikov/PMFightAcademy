package krasnikov.project.pmfightacademy.app.data

import krasnikov.project.pmfightacademy.app.data.pref.SharedPref
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(/*private val sharedPref: SharedPref*/) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request =chain.request()
            .newBuilder()
            .addHeader("accept","text/plain")
            .addHeader("Content-Type","application/json")
            .build()

        return chain.proceed(request)
    }
}