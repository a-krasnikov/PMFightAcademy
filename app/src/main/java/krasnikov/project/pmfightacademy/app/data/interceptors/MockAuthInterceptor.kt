package krasnikov.project.pmfightacademy.app.data.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class MockAuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.request()

        val authRequest = response.newBuilder().addHeader(
            "Authorization",
            "bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoiMDk5MTIzNDU2NyIsImh0dHA6Ly9zY2hlbWFzLm1pY3Jvc29mdC5jb20vd3MvMjAwOC8wNi9pZGVudGl0eS9jbGFpbXMvdXNlcmRhdGEiOiI0MiIsIm5iZiI6MTYxNjUxNTQwMiwiZXhwIjoxNjE3MTIwMjAyLCJpc3MiOiJNeUF1dGhTZXJ2ZXIiLCJhdWQiOiJNeUF1dGhDbGllbnQifQ.Mpc3TVWDFabvq6CYW8OyCT4TVFte4wicCm5jOCe1GEc"
        ).build()

        return chain.proceed(authRequest)
    }
}