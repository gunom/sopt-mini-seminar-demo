package com.example.demosopt.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/test")
@RestController
@ApiResponses(
    value = [
        ApiResponse(responseCode = "200", description = "OK"),
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
    ): ResponseEntity<DefaultParameterResponse> {
        return ResponseEntity.ok(DefaultParameterResponse("page: $page, order: $order, size: $size, sort: $sort"))
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
    ): ResponseEntity<NotDefaultParameterResponse> {
        return ResponseEntity.ok(NotDefaultParameterResponse("page: $page, order: $order, size: $size, sort: $sort"))
    }

    @PostMapping("/with/enum")
    @Operation(summary = "Test with enum")
    fun testWithEnum(
        @RequestParam(value = "enum") enum: EnumRequest
    ): ResponseEntity<EnumResponse> {
        return ResponseEntity.ok(EnumResponse("enum: ${enum.testEnum}"))
    }

    @PostMapping("/with/request-body")
    @Operation(summary = "Test with request body")
    fun testWithRequestBody(
        @RequestBody request: TestRequest
    ): ResponseEntity<RequestBodyResponse> {
        return ResponseEntity.ok(RequestBodyResponse("name: ${request.name}, age: ${request.age}"))
    }

    class TestRequest(
        @field:Schema(description = "이름", required = true, example = "홍길동")
        val name: String,
        @field:Schema(description = "나이", required = true, example = "20")
        val age: Int
    )

    data class DefaultParameterResponse(
        @field:Schema(description = "default parameter response", example = "page: 1, order: desc, size: 10, sort: id")
        val response: String
    )

    data class NotDefaultParameterResponse(
        @field:Schema(description = "not default parameter response", example = "page: 1, order: desc, size: 10, sort: id")
        val response: String
    )

    data class EnumRequest (
        @field:Schema(description = "테스트 enum", example = "FUNNY")
        val testEnum: TestEnum
    )

    data class EnumResponse(
        @field:Schema(description = "enum response", example = "enum: FUNNY")
        val response: String
    )

    data class RequestBodyResponse(
        @field:Schema(description = "request body response", example = "name: 홍길동, age: 20")
        val response: String
    )

    enum class TestEnum {
        FUNNY, SAD, HAPPY
    }
}