package team.aliens.dmscron.global.error

data class ErrorResponse(
    val status: Int,
    val message: String
) {

    companion object {
        fun of(errorProperty: ErrorProperty) = ErrorResponse(
            status = errorProperty.status().value(),
            message = errorProperty.message()
        )
    }
}