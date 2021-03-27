package krasnikov.project.pmfightacademy.app.domain.error

import androidx.annotation.StringRes

enum class GeneralError(@StringRes val errorStringRes: Int) {
    InvalidDataSent(0),
    ConflictingDataSent(0),
    ServerError(0),
    Unknown(0)
}
