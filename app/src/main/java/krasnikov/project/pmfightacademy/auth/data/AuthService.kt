package krasnikov.project.pmfightacademy.auth.data

import krasnikov.project.pmfightacademy.auth.data.model.AccessToken
import krasnikov.project.pmfightacademy.auth.data.model.Login
import krasnikov.project.pmfightacademy.auth.data.model.Register
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/Clients/Login")
    suspend fun login(@Body login: Login): AccessToken

    @POST("/Clients/Register")
    suspend fun register(@Body register: Register): AccessToken
}
