package krasnikov.project.pmfightacademy.activity.booking.services.data

import krasnikov.project.pmfightacademy.app.data.ResponseWithPaginationModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ServicesService {
    @GET("/services")
    suspend fun getServices(
        @Query("pageSize") pageSize: Int,
        @Query("page") page: Int,
    ): ResponseWithPaginationModel<Service>
}
