package com.example.demosopt.config

import com.example.demosopt.authenticaion.CustomAuthenticationEntryPoint
import com.example.demosopt.authenticaion.JwtAuthFilter
import com.example.demosopt.authenticaion.JwtUtil
import com.example.demosopt.service.CustomUserDetailsService
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
class SecurityConfig(
    val customUserDetailsService: CustomUserDetailsService,
    val jwtUtil: JwtUtil,
    val authenticationEntryPoint: CustomAuthenticationEntryPoint
) {
    private val AUTH_WHITELIST = arrayOf("/api/v1/member/**", "/swagger-ui/**", "/api-docs", "/swagger-ui-custom.html",
            "/v3/api-docs/**", "/api-docs/**", "/swagger-ui.html", "/api/auth/**",
    )

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {

        //CSRF, CORS
        http.csrf { csrf -> csrf.disable() }
        http.cors(Customizer.withDefaults())

        //세션 관리 상태 없음으로 구성, Spring Security가 세션 생성 or 사용 X
        http.sessionManagement { sessionManagement ->
            sessionManagement.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS)
        }

        //FormLogin, BasicHttp 비활성화
        http.formLogin { form -> form.disable() }
        http.httpBasic { obj: AbstractHttpConfigurer<*, *> -> obj.disable() }

        //JwtAuthFilter를 UsernamePasswordAuthenticationFilter 앞에 추가
        http.addFilterBefore(JwtAuthFilter(customUserDetailsService, jwtUtil), UsernamePasswordAuthenticationFilter::class.java)

        http.exceptionHandling { exceptionHandling ->
            exceptionHandling
                    .authenticationEntryPoint(authenticationEntryPoint)
        }

        // 권한 규칙 작성
        http.authorizeHttpRequests { authorize ->
            AUTH_WHITELIST.map {
                authorize.requestMatchers(it).permitAll()
            }
            authorize.anyRequest().authenticated()
        }

        return http.build()
    }

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web: WebSecurity ->
            web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations())
        }
    }
}