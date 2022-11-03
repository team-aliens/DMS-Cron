package team.aliens.dmscron.global.error

import org.hibernate.exception.ConstraintViolationException
import org.springframework.boot.context.properties.bind.BindResult
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import team.aliens.dmscron.global.exception.DmsCronException

data class ErrorResponse(
    val status: Int,
    val message: String
) {

    companion object {
        fun of(e: DmsCronException): ResponseEntity<ErrorResponse> {
            val error = e.errorProperty

            return ResponseEntity(
                ErrorResponse(
                    status = error.status(),
                    message = error.message()
                ),
                HttpStatus.valueOf(error.status())
            )
        }
    }
}