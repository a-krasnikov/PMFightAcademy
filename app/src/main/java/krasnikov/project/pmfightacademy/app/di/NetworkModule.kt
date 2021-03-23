package krasnikov.project.pmfightacademy.app.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json
import krasnikov.project.pmfightacademy.activities.data.ActivitiesService
import krasnikov.project.pmfightacademy.app.data.PaginationModel
import krasnikov.project.pmfightacademy.app.data.ResponseWithPaginationModel
import krasnikov.project.pmfightacademy.app.data.interceptors.MockAuthInterceptor
import krasnikov.project.pmfightacademy.coaches.data.Coach
import krasnikov.project.pmfightacademy.coaches.data.CoachesService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Singleton

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
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder().addInterceptor(MockAuthInterceptor()).build()
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

    //TODO Change to retrofit service
    //@Suppress("MagicNumber", "MaxLineLength", "TooGenericExceptionThrown")
    //@OptIn(ExperimentalStdlibApi::class)
    @Provides
    fun provideCoachesService(retrofit: Retrofit): CoachesService {
        return retrofit.create(CoachesService::class.java)
//        return object : CoachesService {
//            private val listData: MutableList<ResponseWithPaginationModel<Coach>> = mutableListOf()
//
//            init {
//                val coach = Coach(
//                    0,
//                    "Alex",
//                    "Tarasov",
//                    20,
//                    "In life I am a versatile person. Persistent, purposeful, sociable. I love to set challenging tasks for myself and fulfill them. Since for me sport is fun up to a sweat, I train and train with true pleasure. I sincerely believe that it is the personal trainer who is responsible for the athletic success of his wards. Give me the opportunity to unleash your potential - and you will not regret it for a second!",
//                    "+380685673421",
//                    listOf("Boxing")
//                )
//
//                val list = buildList {
//                    repeat(30) {
//                        this.add(coach)
//                    }
//                }
//
//                repeat(3) {
//                    listData.add(
//                        ResponseWithPaginationModel(
//                            list,
//                            PaginationModel(
//                                page = it,
//                                totalPages = 3,
//                                hasPreviousPage = it != 0,
//                                hasNextPage = it != 2
//                            )
//                        )
//                    )
//                }
//            }
//
//            override suspend fun getCoaches(
//                pageSize: Int,
//                page: Int
//            ): ResponseWithPaginationModel<Coach> {
//                delay(2000)
//                return listData[page]
//            }
//        }
    }
}
