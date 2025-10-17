package com.example.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        String version = System.getenv("VERSION") != null ? System.getenv("VERSION") : "1.0";
        return "Hello from Blue-Green Spring Boot App! Version: " + version;
    }

    @GetMapping("/greeting")
    public String greeting() {
        String version = System.getenv("VERSION") != null ? System.getenv("VERSION") : "1.0";
        return "{\"id\":1,\"content\":\"Greeting from Blue-Green! Version: " + version + "\"}";
    }
}
