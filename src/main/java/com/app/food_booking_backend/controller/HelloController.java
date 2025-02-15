package com.app.food_booking_backend.controller;

import com.app.food_booking_backend.exception.UnauthorizedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hello")
public class HelloController {

    @GetMapping("")
    public String sayHello() {
        throw new UnauthorizedException("TEST");
    }

}
