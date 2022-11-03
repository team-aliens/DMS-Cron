package team.aliens.dmscron.thirdparty.api.exception

import team.aliens.dmscron.global.exception.DmsCronException
import team.aliens.dmscron.thirdparty.api.error.FeignClientErrorCode

object OtherServerBadRequestException : DmsCronException(
    FeignClientErrorCode.OTHER_SERVER_BAD_REQUEST
)