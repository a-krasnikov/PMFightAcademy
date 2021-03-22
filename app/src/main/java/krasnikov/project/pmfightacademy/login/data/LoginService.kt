package krasnikov.project.pmfightacademy.login.data

import krasnikov.project.pmfightacademy.login.data.model.AccessToken
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginService {
    @Headers("Accept: application/json")
    @POST("/Clients/Login")
    @FormUrlEncoded
    suspend fun getAccessToken(
        @Field("login") login: String,
        @Field("password") password: String
    ): AccessToken

    suspend fun getNewRegistration(
        @Field("login") login: String,
        @Field("password") password: String,
        @Field("name") name:String
    ): AccessToken
}