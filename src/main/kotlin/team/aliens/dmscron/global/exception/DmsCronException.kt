package team.aliens.dmscron.global.exception

import team.aliens.dmscron.global.error.ErrorProperty

abstract class DmsCronException(
    val errorProperty: ErrorProperty
) : RuntimeException() {

    override fun fillInStackTrace() = this
}