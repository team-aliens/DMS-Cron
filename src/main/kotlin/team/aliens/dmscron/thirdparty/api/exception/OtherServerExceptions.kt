package team.aliens.dmscron.thirdparty.api.exception

import team.aliens.dmscron.global.exception.DmsCronException

sealed class OtherServerExceptions(
    override val status: Int,
    override val message: String
) : DmsCronException(status, message) {

    class BadRequest(message: String = BAD_REQUEST) : OtherServerExceptions(400, message)

    class UnAuthorized(message: String = UnAuthorized) : OtherServerExceptions(401, message)

    class Forbidden(message: String = FORBIDDEN) : OtherServerExceptions(403, message)


    companion object {
        private const val BAD_REQUEST = "Other Server Bad Request"
        private const val UnAuthorized = "Other Server Un Authorized"
        private const val FORBIDDEN = "Other Server Forbidden"
    }
}