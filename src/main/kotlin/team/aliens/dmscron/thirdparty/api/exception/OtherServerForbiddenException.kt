package team.aliens.dmscron.thirdparty.api.exception

import team.aliens.dmscron.global.exception.DmsCronException
import team.aliens.dmscron.thirdparty.api.error.FeignClientErrorCode

object OtherServerForbiddenException : DmsCronException(
    FeignClientErrorCode.OTHER_SERVER_FORBIDDEN
)