package krasnikov.project.pmfightacademy.auth.domain.verfication

import krasnikov.project.pmfightacademy.auth.domain.verfication.exceptions.NameNotValidException
import krasnikov.project.pmfightacademy.auth.domain.verfication.exceptions.PasswordNotValidException
import krasnikov.project.pmfightacademy.auth.domain.verfication.exceptions.PhoneNotValidException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

internal class AuthValidatorTest {

    @TestFactory
    fun `phone validation detects invalid phones`(): List<DynamicTest> {
        val authValidator = AuthValidator()

        return listOf(
            "380991234567",
            "12351251595695",
            "991234567"
        ).map { phone ->
            DynamicTest.dynamicTest("Phone number: $phone is invalid") {
                assertThrows(PhoneNotValidException::class.java) {
                    authValidator.validatePhone(phone)
                }
            }
        }
    }

    @Test
    fun `phone validation detects valid phones`() {
        val authValidator = AuthValidator()
        val validPhone = "+380991234567"
        assertDoesNotThrow {
            authValidator.validatePhone(validPhone)
        }
    }

    @TestFactory
    fun `password validation detects invalid passwords`(): List<DynamicTest> {
        val authValidator = AuthValidator()

        return listOf(
            "12351251595695",
            "12351251595695p",
            "password",
            "password1",
            "Aa12345"
        ).map { password ->
            DynamicTest.dynamicTest("Password: $password is invalid") {
                assertThrows(PasswordNotValidException::class.java) {
                    authValidator.validatePassword(password)
                }
            }
        }
    }

    @Test
    fun `password validation detects valid passwords`() {
        val authValidator = AuthValidator()
        val validPassword = "Aa123456"
        assertDoesNotThrow {
            authValidator.validatePassword(validPassword)
        }
    }

    @TestFactory
    fun `name validation detects invalid names`(): List<DynamicTest> {
        val authValidator = AuthValidator()

        return listOf(
            "Name1",
            "A",
        ).map { name ->
            DynamicTest.dynamicTest("Name: $name is invalid") {
                assertThrows(NameNotValidException::class.java) {
                    authValidator.validateName(name)
                }
            }
        }
    }

    @Test
    fun `name validation detects valid names`() {
        val authValidator = AuthValidator()
        val validName = "Name Surname"
        assertDoesNotThrow {
            authValidator.validateName(validName)
        }
    }

}
