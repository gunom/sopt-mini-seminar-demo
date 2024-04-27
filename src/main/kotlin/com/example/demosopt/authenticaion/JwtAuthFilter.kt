package com.example.demosopt.authenticaion

import com.example.demosopt.service.CustomUserDetailsService
import io.jsonwebtoken.io.IOException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthFilter(
        val customUserDetailsService: CustomUserDetailsService,
        val jwtUtil: JwtUtil
) : OncePerRequestFilter() {
    @Throws(ServletException::class, IOException::class)
    /**
     * JWT 토큰 검증 필터 수행
     */
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val authorizationHeader = request.getHeader("Authorization")
        val authService = request.headerNames.toList()
        //JWT가 헤더에 있는 경우
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            val token = authorizationHeader.substring(7)
            //JWT 유효성 검증
            if (jwtUtil.validateToken(token)) {
                val userId = jwtUtil.getUserId(token)

                //유저와 토큰 일치 시 userDetails 생성
                val userDetails: UserDetails = customUserDetailsService.loadUserByUsername(userId.toString())

                if (userDetails != null) {
                    //UserDetsils, Password, Role -> 접근권한 인증 Token 생성
                    val usernamePasswordAuthenticationToken =
                            UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)

                    //현재 Request의 Security Context에 접근권한 설정
                    SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
                }
            }
        }

        filterChain.doFilter(request, response) // 다음 필터로 넘기기
    }
}