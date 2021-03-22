package krasnikov.project.pmfightacademy.coaches.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Coach(
    @SerialName("id")
    val id: Int,
    @SerialName("firstName")
    val firstName: String,
    @SerialName("lastName")
    val lastName: String,
    @SerialName("age")
    val age: Int,
    @SerialName("description")
    val description: String,
    @SerialName("phoneNumber")
    val phoneNumber: String,
    @SerialName("services")
    val services: List<String>,
)
