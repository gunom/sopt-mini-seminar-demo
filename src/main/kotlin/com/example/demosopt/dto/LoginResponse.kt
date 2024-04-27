package com.example.demosopt.dto

import io.swagger.v3.oas.annotations.media.Schema

data class LoginResponse(
    @Schema(description = "액세스 토큰")
    val accessToken: String,
    @Schema(description = "토큰 타입")
    val tokenType: String = "Bearer",
)
