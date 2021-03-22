package krasnikov.project.pmfightacademy.activities.data

import kotlinx.serialization.SerialName

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
    val coachLastName: String
)
