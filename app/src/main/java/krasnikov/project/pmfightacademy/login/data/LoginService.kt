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
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("code") code: String,
    ): AccessToken
}