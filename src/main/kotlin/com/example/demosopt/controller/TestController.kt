package com.example.demosopt.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/test")
@RestController
@ApiResponses(
    value = [
        ApiResponse(responseCode = "200", description = "OK"),
        ApiResponse(responseCode = "400", description = "Bad Request"),
        ApiResponse(responseCode = "404", description = "Not Found"),
        ApiResponse(responseCode = "500", description = "Internal Server Error")
    ]
)
class TestController {

    @GetMapping("/with/default-param")
    @Operation(summary = "Test with default param")
    fun testWithDefaultParam(
        @RequestParam(value = "page", defaultValue = "1", required = true) page: Int,
        @RequestParam(value = "order", defaultValue = "desc", required = true) order: String,
        @RequestParam(value = "size", defaultValue = "10", required = true) size: Int,
        @RequestParam(value = "sort", defaultValue = "id", required = true) sort: String
    ): ResponseEntity<String> {
        return ResponseEntity.ok("page: $page, order: $order, size: $size, sort: $sort")
    }

    @GetMapping("/without/default-param")
    @Operation(summary = "Test without default param")
    fun testWithoutDefaultParam(
        @Parameter(description = "페이지 번호", required = true)
        @RequestParam(value = "page") page: Int,
        @Parameter(description = "정렬 순서", required = true)
        @RequestParam(value = "order") order: String,
        @Parameter(description = "페이지 크기", required = true)
        @RequestParam(value = "size") size: Int,
        @Parameter(description = "정렬 기준", required = true)
        @RequestParam(value = "sort") sort: String
    ): ResponseEntity<String> {
        return ResponseEntity.ok("page: $page, order: $order, size: $size, sort: $sort")
    }

    @PostMapping("/with/enum")
    @Operation(summary = "Test with enum")
    fun testWithEnum(
        @RequestParam(value = "enum") enum: EnumRequest
    ): ResponseEntity<String> {
        return ResponseEntity.ok("enum: $enum")
    }

    @PostMapping("/with/request-body")
    @Operation(summary = "Test with request body")
    fun testWithRequestBody(
        @RequestBody request: TestRequest
    ): ResponseEntity<String> {
        return ResponseEntity.ok("name: ${request.name}, age: ${request.age}")
    }

    class TestRequest(
        @field:Schema(description = "이름", required = true, example = "홍길동")
        val name: String,
        @field:Schema(description = "나이", required = true, example = "20")
        val age: Int
    )
}