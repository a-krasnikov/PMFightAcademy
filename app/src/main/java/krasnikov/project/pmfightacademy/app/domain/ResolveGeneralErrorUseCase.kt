package krasnikov.project.pmfightacademy.app.domain

import krasnikov.project.pmfightacademy.app.data.exception.ConflictingDataSentException
import krasnikov.project.pmfightacademy.app.data.exception.InvalidDataSentException
import krasnikov.project.pmfightacademy.app.data.exception.ServerErrorException
import krasnikov.project.pmfightacademy.app.domain.error.GeneralError
import krasnikov.project.pmfightacademy.utils.ErrorWrapper
import javax.inject.Inject


class ResolveGeneralErrorUseCase @Inject constructor() {

    fun execute(throwable: Throwable) : ErrorWrapper.General {
        return when(throwable) {
            is InvalidDataSentException -> ErrorWrapper.General(GeneralError.InvalidDataSent)
            is ConflictingDataSentException -> ErrorWrapper.General(GeneralError.ConflictingDataSent)
            is ServerErrorException -> ErrorWrapper.General(GeneralError.ServerError)
            else -> ErrorWrapper.General(GeneralError.Unknown)
        }
    }
}

