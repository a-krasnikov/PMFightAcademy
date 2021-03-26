package krasnikov.project.pmfightacademy.coaches.data

import krasnikov.project.pmfightacademy.app.data.ResponseWithPaginationModel
import retrofit2.http.GET
import retrofit2.http.Path

interface CoachesService {

    @GET("/Coaches/{pageSize}/{page}")
    suspend fun getCoaches(
        @Path("pageSize") pageSize: Int,
        @Path("page") page: Int,
    ): ResponseWithPaginationModel<Coach>
}
