package krasnikov.project.pmfightacademy.app.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json
import krasnikov.project.pmfightacademy.app.data.PaginationModel
import krasnikov.project.pmfightacademy.app.data.ResponseWithPaginationModel
import krasnikov.project.pmfightacademy.coaches.data.Coach
import krasnikov.project.pmfightacademy.coaches.data.CoachesService
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Converter
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideConverterFactory(): Converter.Factory {
        return Json { ignoreUnknownKeys = true; coerceInputValues = true }
            .asConverterFactory("application/json".toMediaType())
    }

    //TODO Change to retrofit service
    @OptIn(ExperimentalStdlibApi::class)
    @Provides
    fun provideCoachesService(): CoachesService {
        return object : CoachesService {
            private val listData: MutableList<ResponseWithPaginationModel<Coach>> = mutableListOf()

            init {
                val coach = Coach(
                    0,
                    "Alex",
                    "Tarasov",
                    20,
                    "In life I am a versatile person. Persistent, purposeful, sociable. I love to set challenging tasks for myself and fulfill them. Since for me sport is fun up to a sweat, I train and train with true pleasure. I sincerely believe that it is the personal trainer who is responsible for the athletic success of his wards. Give me the opportunity to unleash your potential - and you will not regret it for a second!",
                    "+380685673421",
                    listOf("Boxing")
                )

                val list = buildList {
                    repeat(30) {
                        this.add(coach)
                    }
                }

                repeat(3) {
                    listData.add(
                        ResponseWithPaginationModel(
                            list,
                            PaginationModel(
                                page = it,
                                totalPages = 3,
                                hasPreviousPage = it != 0,
                                hasNextPage = it != 2
                            )
                        )
                    )
                }
            }

            override suspend fun getCoaches(pageSize: Int, page: Int): ResponseWithPaginationModel<Coach> {
                delay(2000)
                return listData[page]
            }
        }
    }
}
