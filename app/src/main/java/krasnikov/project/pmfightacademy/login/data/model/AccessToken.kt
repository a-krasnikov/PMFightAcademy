package krasnikov.project.pmfightacademy.login.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccessToken(
    val token: String?,
)