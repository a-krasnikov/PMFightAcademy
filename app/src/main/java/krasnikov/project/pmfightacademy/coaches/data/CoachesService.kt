package krasnikov.project.pmfightacademy.coaches.data

import krasnikov.project.pmfightacademy.app.pagination.PagedResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CoachesService {

    @GET("/coaches")
    suspend fun getCoaches(
        @Query("pageSize") pageSize: Int,
        @Query("page") page: Int,
    ): PagedResponse<Coach>
}
