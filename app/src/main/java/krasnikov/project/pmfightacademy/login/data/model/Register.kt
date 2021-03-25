package krasnikov.project.pmfightacademy.login.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Register(
    val login: String?,
    val password: String?,
    val name: String?,
)