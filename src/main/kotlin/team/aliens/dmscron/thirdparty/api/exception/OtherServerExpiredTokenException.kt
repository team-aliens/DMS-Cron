package team.aliens.dmscron.thirdparty.api.exception

import team.aliens.dmscron.global.exception.DmsCronException
import team.aliens.dmscron.thirdparty.api.error.FeignClientErrorCode

object OtherServerExpiredTokenException : DmsCronException(
    FeignClientErrorCode.OTHER_SERVER_EXPIRED_TOKEN
)