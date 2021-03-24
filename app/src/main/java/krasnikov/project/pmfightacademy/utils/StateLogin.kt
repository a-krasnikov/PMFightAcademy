package krasnikov.project.pmfightacademy.utils

import krasnikov.project.pmfightacademy.login.data.model.AccessToken

sealed class StateLogin<out E> {
    object Loading : StateLogin<Nothing>()
    data class Success (val accessToken: AccessToken) : StateLogin<Nothing>()
    data class Error<E>(val error: E) : StateLogin<E>()
}
