package krasnikov.project.pmfightacademy.utils

import krasnikov.project.pmfightacademy.app.domain.error.GeneralError

sealed class ErrorWrapper<out E> {
    data class General(val errorType: GeneralError) : ErrorWrapper<Nothing>()
    data class ClassSpecific<E>(val errorType: E) : ErrorWrapper<E>()
}
