package com.example.demosopt.authenticaion

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.io.IOException
import java.time.LocalDateTime

@Component
class CustomAuthenticationEntryPoint(
        val objectMapper: ObjectMapper
) : AuthenticationEntryPoint {
    private val log = org.slf4j.LoggerFactory.getLogger(CustomAuthenticationEntryPoint::class.java)

    @Throws(IOException::class, ServletException::class)
    override fun commence(request: HttpServletRequest?,
                          response: HttpServletResponse,
                          authException: AuthenticationException) {
        log.error("Not Authenticated Request", authException)

        val errorResponseDto: ErrorResponseDto = ErrorResponseDto(HttpStatus.UNAUTHORIZED.value(), authException.message!!, LocalDateTime.now())

        val responseBody = objectMapper.writeValueAsString(errorResponseDto)
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.status = HttpStatus.UNAUTHORIZED.value()
        response.characterEncoding = "UTF-8"
        response.writer.write(responseBody)
    }

    class ErrorResponseDto(val status: Int, val message: String, val timestamp: LocalDateTime)
}