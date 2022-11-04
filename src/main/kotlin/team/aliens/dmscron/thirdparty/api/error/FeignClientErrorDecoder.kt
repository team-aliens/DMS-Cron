package team.aliens.dmscron.thirdparty.api.error

import feign.FeignException
import feign.Response
import feign.codec.ErrorDecoder
import org.springframework.http.HttpStatus
import team.aliens.dmscron.thirdparty.api.exception.OtherServerBadRequestException
import team.aliens.dmscron.thirdparty.api.exception.OtherServerForbiddenException
import team.aliens.dmscron.thirdparty.api.exception.OtherServerUnAuthorizedException

class FeignClientErrorDecoder : ErrorDecoder {

    override fun decode(methodKey: String, response: Response): FeignException {
        if (response.status() >= HttpStatus.BAD_REQUEST.value()) {
            when (response.status()) {
                HttpStatus.UNAUTHORIZED.value() -> throw OtherServerUnAuthorizedException
                HttpStatus.FORBIDDEN.value() -> throw OtherServerForbiddenException
                else -> throw OtherServerBadRequestException
            }
        }

        return FeignException.errorStatus(methodKey, response)
    }
}