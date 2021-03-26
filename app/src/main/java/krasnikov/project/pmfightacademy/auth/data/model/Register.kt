package krasnikov.project.pmfightacademy.auth.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Register(
    @SerialName("login")
    val phone: String,
    @SerialName("password")
    val password: String,
    @SerialName("name")
    val name: String
)
