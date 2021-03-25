package krasnikov.project.pmfightacademy.login.data

import krasnikov.project.pmfightacademy.login.data.model.AccessToken
import krasnikov.project.pmfightacademy.login.data.model.Login
import krasnikov.project.pmfightacademy.login.data.model.Register
import retrofit2.Response
import retrofit2.http.*

interface LoginService {
    @POST("/Clients/Login")
    suspend fun getAccessToken(
        @Body login: Login?,
    ): AccessToken

    @POST("/Clients/Register")
    suspend fun getNewRegistration(
        @Body register: Register?,
    ): AccessToken
}