package krasnikov.project.pmfightacademy.app.domain

import io.kotlintest.shouldBe
import krasnikov.project.pmfightacademy.app.data.exception.ConflictingDataSentException
import krasnikov.project.pmfightacademy.app.data.exception.InvalidDataSentException
import krasnikov.project.pmfightacademy.app.data.exception.ServerErrorException
import krasnikov.project.pmfightacademy.app.domain.error.GeneralError
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import java.lang.Exception

internal class ResolveGeneralErrorUseCaseTest {

    @TestFactory
    fun `general errors are resolved correctly`(): List<DynamicTest> {
        val resolveGeneralErrorUseCase = ResolveGeneralErrorUseCase()
        val unknownException = Exception("Unknown")

        return listOf(
            ConflictingDataSentException() to GeneralError.ConflictingDataSent,
            InvalidDataSentException() to GeneralError.InvalidDataSent,
            ServerErrorException() to GeneralError.ServerError,
            unknownException to GeneralError.Unknown
        ).map { (exception, error) ->
            DynamicTest.dynamicTest("Exception: $exception yields error: $error") {
                resolveGeneralErrorUseCase.execute(exception).errorType shouldBe error
            }
        }
    }

}
