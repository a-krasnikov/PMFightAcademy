package krasnikov.project.pmfightacademy.auth.login.domain

import io.kotlintest.shouldBe
import io.mockk.every
import io.mockk.mockk
import krasnikov.project.pmfightacademy.app.domain.ResolveGeneralErrorUseCase
import krasnikov.project.pmfightacademy.app.domain.error.GeneralError
import krasnikov.project.pmfightacademy.auth.domain.verfication.exceptions.PasswordNotValidException
import krasnikov.project.pmfightacademy.auth.domain.verfication.exceptions.PhoneNotValidException
import krasnikov.project.pmfightacademy.utils.ErrorWrapper
import org.junit.internal.runners.statements.ExpectException
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import org.junit.rules.ExpectedException
import java.lang.Exception

internal class ResolveLoginErrorUseCaseTest {

    @TestFactory
    fun `login errors are resolved correctly`(): List<DynamicTest> {
        val resolveGeneralErrorUseCaseMock = mockk<ResolveGeneralErrorUseCase>()
        val generalException = Exception("General exception")
        every {
            resolveGeneralErrorUseCaseMock.execute(generalException)
        } returns ErrorWrapper.General(GeneralError.Unknown)

        val resolveLoginErrorUseCase = ResolveLoginErrorUseCase(resolveGeneralErrorUseCaseMock)

        return listOf(
            PhoneNotValidException() to ErrorWrapper.ClassSpecific(LoginError.UserPhoneInvalid),
            PasswordNotValidException() to ErrorWrapper.ClassSpecific(LoginError.UserPasswordInvalid),
            generalException to ErrorWrapper.General(GeneralError.Unknown)
        ).map { (exception, error) ->
            DynamicTest.dynamicTest("Exception: $exception yields error: $error") {
                resolveLoginErrorUseCase.execute(exception) shouldBe error
            }
        }
    }

}
