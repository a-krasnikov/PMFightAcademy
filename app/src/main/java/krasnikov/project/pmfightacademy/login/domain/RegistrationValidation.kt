package krasnikov.project.pmfightacademy.login.domain

import krasnikov.project.pmfightacademy.login.data.model.Login
import krasnikov.project.pmfightacademy.login.data.model.Register
import javax.inject.Inject

//TODO -> закончить валидацию

class RegistrationValidation @Inject constructor(
    private val register: Register,
) {
    fun getRegisterValidation(register: Register): Boolean {
        return getRegisterValidationLogin(register) &&
                getRegisterValidationPassword(register) &&
                getRegisterValidationName(register)
    }

    private fun getRegisterValidationLogin(register: Register): Boolean {
        val validationLogin = register.login
//        if (true) {
        return true
//            для номеру телефону "^\\+380[5-9][0-9]\\d{7}$"
//            для паролю повністю "(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z]).{8,}", але я собі розбив на три окремих щоб зручніше помилки користувачу відображати
//        }
    }

    private fun getRegisterValidationPassword(register: Register): Boolean {
        val validationPassword = register.password
        return true
    }

    private fun getRegisterValidationName(register: Register): Boolean {
        val validationName = register.name
        return validationName?.length!! >= 2
    }
}