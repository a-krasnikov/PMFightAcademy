package krasnikov.project.pmfightacademy.login.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Login(
    val login:String?,
    val password:String?
)
