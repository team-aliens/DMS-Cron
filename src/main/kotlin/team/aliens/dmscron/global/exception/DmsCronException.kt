package team.aliens.dmscron.global.exception

abstract class DmsCronException(
    open val status: Int,
    override val message: String
) : RuntimeException() {

    override fun fillInStackTrace() = this

}