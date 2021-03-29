package krasnikov.project.pmfightacademy.activity.activities.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Activity(
    @SerialName("serviceName")
    val serviceName: String,
    @SerialName("date")
    val date: String,
    @SerialName("time")
    val time: String,
    @SerialName("coachFirstName")
    val coachFirstName: String,
    @SerialName("coachLastName")
    val coachLastName: String,
    @SerialName("price")
    val price: Int
)
