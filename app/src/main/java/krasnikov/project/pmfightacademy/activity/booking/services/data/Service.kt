package krasnikov.project.pmfightacademy.activity.booking.services.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Service(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("price")
    val price: Int
)
