package team.aliens.dmscron.global.exception

import team.aliens.dmscron.global.error.GlobalErrorCode

object InternalServerErrorException : DmsCronException(
    GlobalErrorCode.INTERNAL_SERVER_ERROR
)