package team.aliens.dmscron.global.error

enum class GlobalErrorCode(
    private val status: Int,
    private val message: String
) : ErrorProperty {

    ;

    override fun status(): Int = status
    override fun message(): String = message
}