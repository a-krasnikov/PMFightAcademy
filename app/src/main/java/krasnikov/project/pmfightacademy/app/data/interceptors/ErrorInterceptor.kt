package krasnikov.project.pmfightacademy.app.data.interceptors

import krasnikov.project.pmfightacademy.app.data.exception.ClientNotAuthorizedException
import krasnikov.project.pmfightacademy.app.data.exception.ConflictingDataSentException
import krasnikov.project.pmfightacademy.app.data.exception.InvalidDataSentException
import krasnikov.project.pmfightacademy.app.data.exception.ServerErrorException
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ErrorInterceptor @Inject constructor() : Interceptor {
    private companion object {
        const val HTTP_NOT_AUTHORIZED = 401
        const val HTTP_BAD_REQUEST = 400
        const val HTTP_CONFLICT = 409
        val HTTP_SERVER_ERRORS = 500..505
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        when(response.code) {
            HTTP_NOT_AUTHORIZED -> {
                throw ClientNotAuthorizedException()
            }
            HTTP_BAD_REQUEST -> {
                throw InvalidDataSentException()
            }
            HTTP_CONFLICT -> {
                throw ConflictingDataSentException()
            }
            in HTTP_SERVER_ERRORS -> {
                throw ServerErrorException()
            }
            else -> {
                return response
            }
        }
    }
}
