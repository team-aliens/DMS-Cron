package team.aliens.dmscron.global.filter

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType
import org.springframework.web.filter.OncePerRequestFilter
import team.aliens.dmscron.global.error.ErrorResponse
import team.aliens.dmscron.global.exception.DmsCronException
import java.lang.Exception
import java.nio.charset.StandardCharsets
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import team.aliens.dmscron.global.exception.GlobalExceptions

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
                is DmsCronException -> errorToJson((e.cause as DmsCronException), response)
                else -> {
                    errorToJson(GlobalExceptions.InternalServerError(), response)
                    e.printStackTrace()
                }
            }
        }
    }

    private fun errorToJson(exception: DmsCronException, response: HttpServletResponse) {
        response.status = exception.status
        response.characterEncoding = StandardCharsets.UTF_8.name()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.writer.write(
            objectMapper.writeValueAsString(
                ErrorResponse.of(exception)
            )
        )
    }
}