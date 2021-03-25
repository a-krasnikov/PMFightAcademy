package krasnikov.project.pmfightacademy.activities.data

import krasnikov.project.pmfightacademy.app.data.ResponseWithPaginationModel
import retrofit2.http.GET
import retrofit2.http.Path

interface ActivitiesService {
    @GET("/Booking/active/{pageSize}/{page}")
    suspend fun getPlannedActivities(
        @Path("pageSize") pageSize: Int,
        @Path("page") page: Int
    ): ResponseWithPaginationModel<Activity>

    @GET("/Booking/history/{pageSize}/{page}")
    suspend fun getActivitiesHistory(
        @Path("pageSize") pageSize: Int,
        @Path("page") page: Int
    ): ResponseWithPaginationModel<Activity>
}
