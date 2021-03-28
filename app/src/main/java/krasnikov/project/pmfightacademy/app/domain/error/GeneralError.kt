package krasnikov.project.pmfightacademy.app.domain.error

import androidx.annotation.StringRes
import krasnikov.project.pmfightacademy.R

enum class GeneralError(@StringRes val errorStringRes: Int) {
    InvalidDataSent(R.string.error_data_could_not_be_loaded),
    ConflictingDataSent(R.string.such_user_already_exists),
    ServerError(R.string.server_error),
    Unknown(R.string.unknown_error)
}
