package team.aliens.dmscron.global.exception

sealed class GlobalExceptions(
    override val status: Int,
    override val message: String
) : DmsCronException(status, message) {

    class InternalServerError(message: String = INTERNAL_SERVER_ERROR) : GlobalExceptions(500, message)

    companion object {
        private const val INTERNAL_SERVER_ERROR = "Internal Server Error"
    }
}