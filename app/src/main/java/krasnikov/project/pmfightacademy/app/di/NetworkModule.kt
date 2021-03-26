package krasnikov.project.pmfightacademy.app.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import krasnikov.project.pmfightacademy.activities.data.ActivitiesService
import krasnikov.project.pmfightacademy.app.data.interceptors.AuthInterceptor
import krasnikov.project.pmfightacademy.app.data.pref.SharedPref
import krasnikov.project.pmfightacademy.coaches.data.CoachesService
import krasnikov.project.pmfightacademy.auth.data.AuthService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Singleton

@Suppress("MagicNumber", "MaxLineLength")
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://pmfightacademyclient-92m8i.ondigitalocean.app"

    @Singleton
    @Provides
    fun provideConverterFactory(): Converter.Factory {
        return Json { ignoreUnknownKeys = true; coerceInputValues = true }
            .asConverterFactory("application/json".toMediaType())
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(sharedPref: SharedPref): OkHttpClient {
        return OkHttpClient().newBuilder().addInterceptor(AuthInterceptor(sharedPref)).build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, converterFactory: Converter.Factory): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(converterFactory)
            .build()
    }

    @Provides
    fun provideActivitiesService(retrofit: Retrofit): ActivitiesService {
        return retrofit.create(ActivitiesService::class.java)
    }


    @Provides
    fun provideCoachesService(retrofit: Retrofit): CoachesService {
        return retrofit.create(CoachesService::class.java)
    }

    @Provides
    fun provideLoginService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }
}
