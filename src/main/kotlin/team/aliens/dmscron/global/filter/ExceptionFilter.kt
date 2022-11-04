package team.aliens.dmscron.global.filter

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType
import org.springframework.web.filter.OncePerRequestFilter
import team.aliens.dmscron.global.error.ErrorProperty
import team.aliens.dmscron.global.error.ErrorResponse
import team.aliens.dmscron.global.exception.DmsCronException
import team.aliens.dmscron.global.exception.InternalServerErrorException
import java.lang.Exception
import java.nio.charset.StandardCharsets
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ExceptionFilter(
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: Exception) {
            when (e) {
                is DmsCronException -> errorToJson((e.cause as DmsCronException).errorProperty, response)
                else -> {
                    errorToJson(InternalServerErrorException.errorProperty, response)
                    e.printStackTrace()
                }
            }
        }
    }

    private fun errorToJson(errorProperty: ErrorProperty, response: HttpServletResponse) {
        response.status = errorProperty.status()
        response.characterEncoding = StandardCharsets.UTF_8.name()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.writer.write(objectMapper.writeValueAsString(ErrorResponse.of(errorProperty)))
    }
}