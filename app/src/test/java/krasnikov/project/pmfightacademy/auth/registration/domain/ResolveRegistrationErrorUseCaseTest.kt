package krasnikov.project.pmfightacademy.auth.registration.domain

import io.kotlintest.shouldBe
import io.mockk.every
import io.mockk.mockk
import krasnikov.project.pmfightacademy.app.domain.ResolveGeneralErrorUseCase
import krasnikov.project.pmfightacademy.app.domain.error.GeneralError
import krasnikov.project.pmfightacademy.auth.domain.verfication.exceptions.NameNotValidException
import krasnikov.project.pmfightacademy.auth.domain.verfication.exceptions.PasswordNotValidException
import krasnikov.project.pmfightacademy.auth.domain.verfication.exceptions.PhoneNotValidException
import krasnikov.project.pmfightacademy.utils.ErrorWrapper
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import java.lang.Exception

internal class ResolveRegistrationErrorUseCaseTest {

    @TestFactory
    fun `registration errors are resolved correctly`(): List<DynamicTest> {
        val resolveGeneralErrorUseCaseMock = mockk<ResolveGeneralErrorUseCase>()
        val generalException = Exception("General exception")
        every {
            resolveGeneralErrorUseCaseMock.execute(generalException)
        } returns ErrorWrapper.General(GeneralError.Unknown)

        val resolveRegistrationErrorUseCase =
            ResolveRegistrationErrorUseCase(resolveGeneralErrorUseCaseMock)

        return listOf(
            PhoneNotValidException() to ErrorWrapper.ClassSpecific(RegistrationError.UserPhoneInvalid),
            PasswordNotValidException() to ErrorWrapper.ClassSpecific(RegistrationError.UserPasswordInvalid),
            NameNotValidException() to ErrorWrapper.ClassSpecific(RegistrationError.UserNameInvalid),
            generalException to ErrorWrapper.General(GeneralError.Unknown)
        ).map { (exception, error) ->
            DynamicTest.dynamicTest("Exception: $exception yields error: $error") {
                resolveRegistrationErrorUseCase.execute(exception) shouldBe error
            }
        }
    }

}
