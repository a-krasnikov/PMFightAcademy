package krasnikov.project.pmfightacademy.auth.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccessToken(
    @SerialName("token")
    val token: String
)
