package krasnikov.project.pmfightacademy.activity.booking.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Booking(
    @SerialName("date")
    val date: String,
    @SerialName("time")
    val time: String,
    @SerialName("serviceId")
    val serviceId: Int,
    @SerialName("coachId")
    val coachId: Int,
)
