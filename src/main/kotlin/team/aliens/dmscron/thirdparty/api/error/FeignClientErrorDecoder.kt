package team.aliens.dmscron.thirdparty.api.error

import feign.FeignException
import feign.Response
import feign.codec.ErrorDecoder
import org.springframework.http.HttpStatus
import team.aliens.dmscron.thirdparty.api.exception.OtherServerExceptions

class FeignClientErrorDecoder : ErrorDecoder {

    override fun decode(methodKey: String, response: Response): FeignException {
        if (response.status() >= HttpStatus.BAD_REQUEST.value()) {
            when (response.status()) {
                HttpStatus.UNAUTHORIZED.value() -> throw OtherServerExceptions.UnAuthorized()
                HttpStatus.FORBIDDEN.value() -> throw OtherServerExceptions.Forbidden()
                else -> throw OtherServerExceptions.BadRequest()
            }
        }

        return FeignException.errorStatus(methodKey, response)
    }
}