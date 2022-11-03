package team.aliens.dmscron.thirdparty.api.error

import team.aliens.dmscron.global.error.ErrorProperty

enum class FeignClientErrorCode(
    private val status: Int,
    private val message: String
) : ErrorProperty {

    OTHER_SERVER_BAD_REQUEST(400, "Other Server Bad Request"),
    OTHER_SERVER_UN_AUTHORIZED(401, "Other Server Un Authorized"),
    OTHER_SERVER_FORBIDDEN(403, "Other Server Forbidden"),
    OTHER_SERVER_EXPIRED_TOKEN(419, "Other Server Expired Token")
    ;

    override fun status(): Int = status
    override fun message(): String = message
}