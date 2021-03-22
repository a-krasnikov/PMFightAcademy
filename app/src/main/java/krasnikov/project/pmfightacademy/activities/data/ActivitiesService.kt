package krasnikov.project.pmfightacademy.activities.data

import krasnikov.project.pmfightacademy.app.pagination.PagedResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ActivitiesService {
    @GET("/Booking/{pageSize}/{page}")
    fun getPlannedActivities(
        @Path("pageSize") pageSize: Int,
        @Path("page") page: Int
    ): PagedResponse<Activity>

    @GET("/Booking/history/{pageSize}/{page}")
    fun getActivitiesHistory(
        @Path("pageSize") pageSize: Int,
        @Path("page") page: Int
    ): PagedResponse<Activity>
}
