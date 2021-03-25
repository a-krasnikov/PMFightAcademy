package krasnikov.project.pmfightacademy.activity.booking.data

import krasnikov.project.pmfightacademy.activity.booking.services.data.Service
import krasnikov.project.pmfightacademy.app.data.ResponseWithPaginationModel
import krasnikov.project.pmfightacademy.coaches.data.Coach
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BookingService {

    @GET("/Booking/{pageSize}/{page}")
    suspend fun getServices(
        @Path("pageSize") pageSize: Int,
        @Path("page") page: Int,
    ): ResponseWithPaginationModel<Service>

    @GET("/Booking/{serviceId}/{pageSize}/{page}")
    suspend fun getCoaches(
        @Path("serviceId") serviceId: Int,
        @Path("pageSize") pageSize: Int,
        @Path("page") page: Int,
    ): ResponseWithPaginationModel<Coach>

    @GET("/Booking/{serviceId}/{coachId}")
    suspend fun getAvailableDates(
        @Path("serviceId") serviceId: Int,
        @Path("coachId") coachId: Int,
    ): List<String>

    @GET("/Booking/{serviceId}/{coachId}/{date}")
    suspend fun getAvailableTimeSlots(
        @Path("serviceId") serviceId: Int,
        @Path("coachId") coachId: Int,
        @Path("date") date: String,
    ): List<String>

    @POST("/Booking")
    suspend fun book(@Body booking: Booking)
}
