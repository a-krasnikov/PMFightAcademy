package krasnikov.project.pmfightacademy.auth.domain.verfication

import krasnikov.project.pmfightacademy.auth.domain.verfication.exceptions.NameNotValidException
import krasnikov.project.pmfightacademy.auth.domain.verfication.exceptions.PasswordNotValidException
import krasnikov.project.pmfightacademy.auth.domain.verfication.exceptions.PhoneNotValidException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

internal class AuthValidatorTest {

    @TestFactory
    fun `phone validation works as intended`(): List<DynamicTest> {
        val authValidator = AuthValidator()

        val testList = listOf(
            "380991234567",
            "12351251595695",
            "991234567"
        ).map { phone ->
            DynamicTest.dynamicTest("Phone number: $phone is invalid") {
                assertThrows(PhoneNotValidException::class.java) {
                    authValidator.validatePhone(phone)
                }
            }
        }.toMutableList()
        val validPhone = "+380991234567"
        testList.add(
            DynamicTest.dynamicTest("Phone number: $validPhone is valid") {
                assertDoesNotThrow {
                    authValidator.validatePhone(validPhone)
                }
            }
        )

        return testList
    }

    @TestFactory
    fun `password validation works as intended`(): List<DynamicTest> {
        val authValidator = AuthValidator()

        val testList = listOf(
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
        }.toMutableList()
        val validPassword = "Aa123456"
        testList.add(
            DynamicTest.dynamicTest("Password: $validPassword is valid") {
                assertDoesNotThrow {
                    authValidator.validatePassword(validPassword)
                }
            }
        )

        return testList
    }

    @TestFactory
    fun `name validation works as intended`(): List<DynamicTest> {
        val authValidator = AuthValidator()

        val testList = listOf(
            "Name1",
            "A",
        ).map { name ->
            DynamicTest.dynamicTest("Name: $name is invalid") {
                assertThrows(NameNotValidException::class.java) {
                    authValidator.validateName(name)
                }
            }
        }.toMutableList()
        val validName = "Name Surname"
        testList.add(
            DynamicTest.dynamicTest("Name: $validName is valid") {
                assertDoesNotThrow {
                    authValidator.validateName(validName)
                }
            }
        )

        return testList
    }

}
