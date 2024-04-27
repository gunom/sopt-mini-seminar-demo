package com.example.demosopt.dto

data class RefreshResponse(
        val accessToken: String,
        val refreshToken: String,
        val tokenType: String = "Bearer",
)