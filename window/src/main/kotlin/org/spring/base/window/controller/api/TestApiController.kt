package org.spring.base.window.controller.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping( "/api/test")
class TestApiController {

    @GetMapping("/hello")
    fun getTest(): String{
        return "Hello World!"
    }
}
