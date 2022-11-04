package team.aliens.dmscron.thirdparty.api.error

import org.springframework.http.HttpStatus
import team.aliens.dmscron.global.error.ErrorProperty

enum class FeignClientErrorCode(
    private val status: HttpStatus,
    private val message: String
) : ErrorProperty {

    OTHER_SERVER_BAD_REQUEST(HttpStatus.BAD_REQUEST, "Other Server Bad Request"),

    OTHER_SERVER_UN_AUTHORIZED(HttpStatus.UNAUTHORIZED, "Other Server Un Authorized"),

    OTHER_SERVER_FORBIDDEN(HttpStatus.FORBIDDEN, "Other Server Forbidden")
    ;

    override fun status(): HttpStatus = status
    override fun message(): String = message
}