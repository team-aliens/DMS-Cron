package team.aliens.dmscron.global.error

import team.aliens.dmscron.global.exception.DmsCronException

data class ErrorResponse(
    val status: Int,
    val message: String
) {

    companion object {
        fun of(exception: DmsCronException) = ErrorResponse(
            status = exception.status,
            message = exception.message
        )
    }
}