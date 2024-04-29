package com.example.demosopt.dto

import io.swagger.v3.oas.annotations.media.Schema

data class LoginResponse(
    @field:Schema(description = "액세스 토큰", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiJ9")
    val accessToken: String,
    @field:Schema(description = "토큰 타입", example = "Bearer")
    val tokenType: String = "Bearer",
)
