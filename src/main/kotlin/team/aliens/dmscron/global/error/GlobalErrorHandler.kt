package team.aliens.dmscron.global.error

import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import team.aliens.dmscron.global.exception.DmsCronException

@RestControllerAdvice
class GlobalErrorHandler {

    @ExceptionHandler(DmsCronException::class)
    protected fun handleException(e: DmsCronException) = ErrorResponse.of(e)
}