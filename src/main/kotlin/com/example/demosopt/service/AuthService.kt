package com.example.demosopt.service

import com.example.demosopt.authenticaion.JwtUtil
import org.springframework.stereotype.Service


@Service
class AuthService(
        val jwtUtil: JwtUtil,
) {

    fun login(): String {
        val accessToken = jwtUtil.createAccessToken()
        return accessToken
    }
}