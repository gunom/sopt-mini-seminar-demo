package com.example.demosopt.controller

import io.swagger.v3.oas.annotations.media.Schema

enum class TestEnum {
    @Schema(description = "씨")
    IS,
    @Schema(description = "재훈")
    JAEHOON,
    @Schema(description = "즐거우세요")
    FUNNY,
    @Schema(description = "ㅎ")
    H
}
