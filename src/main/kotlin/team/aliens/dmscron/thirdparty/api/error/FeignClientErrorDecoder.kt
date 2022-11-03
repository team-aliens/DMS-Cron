package team.aliens.dmscron.thirdparty.api.error

import feign.FeignException
import feign.Response
import feign.codec.ErrorDecoder
import team.aliens.dmscron.thirdparty.api.exception.OtherServerBadRequestException
import team.aliens.dmscron.thirdparty.api.exception.OtherServerExpiredTokenException
import team.aliens.dmscron.thirdparty.api.exception.OtherServerForbiddenException
import team.aliens.dmscron.thirdparty.api.exception.OtherServerUnAuthorizedException

class FeignClientErrorDecoder : ErrorDecoder {

    override fun decode(methodKey: String, response: Response): FeignException {
        if (response.status() >= 400) {
            when (response.status()) {
                401 -> throw OtherServerUnAuthorizedException
                403 -> throw OtherServerForbiddenException
                419 -> throw OtherServerExpiredTokenException
                else -> throw OtherServerBadRequestException
            }
        }

        return FeignException.errorStatus(methodKey, response)
    }
}