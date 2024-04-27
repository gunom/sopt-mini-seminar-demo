package com.example.demosopt.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/health")
class HealthCheckController {
        @GetMapping("/check")
        fun check(): String {
            return "ok"
        }
}