package team.aliens.dmscron.global.error

import org.springframework.http.HttpStatus

data class ErrorResponse(
    val status: HttpStatus,
    val message: String
) {

    companion object {
        fun of(errorProperty: ErrorProperty) = ErrorResponse(
            status = errorProperty.status(),
            message = errorProperty.message()
        )
    }
}