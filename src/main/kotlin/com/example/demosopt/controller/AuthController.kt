package com.example.demosopt.controller

import com.example.demosopt.dto.LoginResponse
import com.example.demosopt.dto.RefreshResponse
import com.example.demosopt.service.AuthService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
@Schema(description = "인증 API")
class AuthController(
    val authService: AuthService
){

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "로그인을 수행합니다.")
    fun login(): LoginResponse {
        val accessToken = authService.login()
       return LoginResponse(accessToken)
    }

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "회원가입을 수행합니다.")
    fun signup(): LoginResponse {
        return LoginResponse("signup")
    }
}