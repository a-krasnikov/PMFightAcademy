package krasnikov.project.pmfightacademy.app.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import krasnikov.project.pmfightacademy.app.data.AuthInterceptor
import krasnikov.project.pmfightacademy.app.data.ErrorInterceptor
import krasnikov.project.pmfightacademy.app.data.pref.SharedPref
import krasnikov.project.pmfightacademy.login.data.LoginService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    companion object {
        const val API_BASE_URL = "https://pmfightacademyclient-92m8i.ondigitalocean.app"
    }

    @Provides
    fun provideConverterFactory(): Converter.Factory {
        return Json { ignoreUnknownKeys = true; coerceInputValues = true }
            .asConverterFactory("application/json".toMediaType())
    }

   /* @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, converterFactory: Converter.Factory): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(API_BASE_URL)
            .addConverterFactory(converterFactory)
            .build()
    }*/

    @Provides
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        errorInterceptor: ErrorInterceptor,
    ): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(authInterceptor)
            .addInterceptor(errorInterceptor)
            .build()
    }

    /*@Provides
    fun provideGsonConverterFactory(
    ): GsonConverterFactory {
        return GsonConverterFactory.create()
    }*/

    @Provides
    fun provideAuthInterceptor(/*sharedPref: SharedPref*/): AuthInterceptor {
        return AuthInterceptor(/*sharedPref*/)
    }

    @Provides
    fun provideLoginService(
        okHttpClient: OkHttpClient,
        converterFactory : Converter.Factory
    ): LoginService {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(API_BASE_URL)
            .addConverterFactory(converterFactory)
            .build().create(LoginService::class.java)
    }
}