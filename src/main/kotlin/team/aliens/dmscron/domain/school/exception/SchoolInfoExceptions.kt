package team.aliens.dmscron.domain.school.exception

import team.aliens.dmscron.global.exception.DmsCronException

sealed class SchoolInfoExceptions(
    override val status: Int,
    override val message: String
) : DmsCronException(status, message) {

    class NotFound(message: String = NOT_FOUND): DmsCronException(404, message)

    companion object {
        private const val NOT_FOUND = "School Info Found"
    }
}
