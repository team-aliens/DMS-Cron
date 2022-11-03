package team.aliens.dmscron.thirdparty.api.exception

import team.aliens.dmscron.global.exception.DmsCronException
import team.aliens.dmscron.thirdparty.api.error.FeignClientErrorCode

object OtherServerUnAuthorizedException : DmsCronException(
    FeignClientErrorCode.OTHER_SERVER_UN_AUTHORIZED
)