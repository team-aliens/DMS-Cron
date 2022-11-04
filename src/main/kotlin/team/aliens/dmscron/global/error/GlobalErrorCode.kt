package team.aliens.dmscron.global.error

enum class GlobalErrorCode(
    private val status: Int,
    private val message: String
) : ErrorProperty {

    INTERNAL_SERVER_ERROR(500, "Internal Server Error")
    ;

    override fun status(): Int = status
    override fun message(): String = message
}