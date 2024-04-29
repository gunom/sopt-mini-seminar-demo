package com.example.demosopt.controller

import io.swagger.v3.oas.annotations.media.Schema

data class EnumRequest (
    @field:Schema(description = "테스트 enum", example = "FUNNY")
    val testEnum: TestEnum
)
